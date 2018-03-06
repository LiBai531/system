package com.physics.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import jxl.Cell;
import jxl.CellView;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.physics.common.exception.BusinessException2;
import com.physics.entity.enumeration.PhysicsEnum;
import com.physics.util.safe.ImageCheckUtil;
import com.itextpdf.text.pdf.PdfReader;

public class FileOperate {
    // 上传后放在哪个位置
    private static Logger logger = Logger.getLogger("FileOperate");

    private static Logger getCurrentLogger() {
        if (logger == null) {
            logger = Logger.getLogger("FileOperate");
        }
        return logger;
    }

    private static final String UPLOADDIR = "import";
    //导出excel存放的位置
    private static final String OUTPUTDIR = "output";

    private static Random       r         = new Random();

    public static String processFilePath(String fileName) {
        if (StringUtils.isBlank(fileName))
            return null;

        fileName = fileName.replaceAll("\\/|\\||:|\\?|\\*|\"|<|>|\\p{Cntrl}", "_");

        return fileName;
    }

    private static enum IncludeType {
        PDF, ZIP, RAR,MP4;
        public static boolean contains(String myType) {
            for (IncludeType type : IncludeType.values()) {
                if (myType.toUpperCase().equals(type.toString()))
                    return true;
            }
            return false;
        }
    }

    /**
     * 根据文件路径查询文件是否存在
     * @param filePath
     *        文件全路径
     * @return
     * @throws Exception
     */
    public static boolean ifFileExist(String filePath) throws Exception {

        if (filePath.startsWith("ftp://"))//如果是ftp
        {
            FTPHelper ftp = new FTPHelper();
            String serverFile = ftp.parseFTPAddress(filePath);
            if (ftp.isExist(serverFile))
                return true;

        } else {//本地文件
            File f = new File(filePath);
            if (f.exists())
                return true;
        }

        return false;
    }

    /**
     * 上传证明文件，为了避免重名，修改文件名，加入上传的单位和时间
     * @param file
     * @param user
     * @return
     */
    public static String uploadFile(MultipartFile file, String userString) {
        // 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中
String realPath = "D:/proofFile/objection/";
        String formerFileName = file.getOriginalFilename();
        String newFileName = getProcessedFileName(formerFileName, userString);
        // 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
        try {
            File newFile = new File(realPath, newFileName);
            InputStream is = file.getInputStream();
            FileUtils.copyInputStreamToFile(is, newFile);
            String fileType = ImageCheckUtil.checkMimeType(file);
            if (StringUtils.isNotBlank((fileType))) {
                if (!ImageCheckUtil.ImageSafeCheck(file))
                    return null;

            }
        } catch (IOException e) {
            return null;
        }
        String filePath = realPath + newFileName;
        return filePath;
    }

    /**
     * 上传文件公共方法
     * @param file
     *        需要上传的文件
     * @param path
     *        上传文件的路径
     * @param fileName
     *        文件名（经过处理后的）
     * @return
     */
    public static Boolean upload(MultipartFile file, String path, String fileName) {
        if (file == null) {
            return false;
        } else {
            try {

                File newFile = new File(path, fileName);
               // String imageType = ImageCheckUtil.checkMimeType(file);
/*                if (StringUtils.isNotBlank((imageType))) {
                    if (!ImageCheckUtil.ImageSafeCheck(file))
                        return false;

                }*/
                InputStream is = file.getInputStream();
                FileUtils.copyInputStreamToFile(is, newFile);

            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    /**
     * 删除附件
     * @param path
     *        文件全路径，类似D:/A/B.xml
     */
    public static void delete(String path) {
        if (path != null) {
            File file = new File(path);
            FileUtils.deleteQuietly(file);
        }
    }

    /**
     * 工具函数，从request中获取MultipartFile对象
     * @param request
     * @return
     */
    public static MultipartFile getFile(HttpServletRequest request) {

        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request
                .getSession().getServletContext());
        commonsMultipartResolver.setDefaultEncoding("utf-8");

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Iterator<String> iter = multipartRequest.getFileNames();
        while (iter.hasNext()) {
            MultipartFile file = multipartRequest.getFile((String) iter.next());
            if (file != null) {
                String fileName = file.getOriginalFilename();
                String suffix = fileName.substring(fileName.lastIndexOf('.') + 1);
                if (IncludeType.contains(suffix)) {
                    return file;
                }
                throw new BusinessException2("FORMAT_ERROR");
            }
        }
        return null;
    }

    /**
     * 生成在 min - max 之间的随机数
     * @param min
     * @param max
     * @return
     */
    private static int getRandomInt(int min, int max) {
        return r.nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 获取处理后的文件名
     * @param formerFileName
     *        原文件名，包含扩展名
     * @param userString
     *        需要在原文件名后添加的后缀字符串
     * @return
     */
    public static String getProcessedFileName(String formerFileName, String userString) {
        formerFileName = processFilePath(formerFileName); //处理00截取
        String[] fileArray = formerFileName.split("\\.");
        String formerSimpleFileName = "";//不带后缀名的原文件名
        String suffix = fileArray[fileArray.length - 1];//原文件的后缀名
        for (int i = 0; i < fileArray.length - 1; i++) {
            formerSimpleFileName = fileArray[i];
        }

        System.out.println(formerSimpleFileName + "_" + userString + "_" + getRandomInt(1000, 9999)
                + "." + suffix);
        return formerSimpleFileName + "_" + userString + "_" + getRandomInt(1000, 9999) + "."
                + suffix;
    }

    /**
     * 导出excel数据的功能函数
     * @param excelTitle
     * @param excelDatalist
     * @param sheetName
     * @param rootPath
     * @param storeFolder
     * @return 返回字符串为将传递给js回调函数生成下载链接提供文件下载功能
     */
    public static Map<String, String> exportExcel(List<List<String>> excelTitle,
            List<List<String[]>> excelDatalist, List<String> sheetName, String rootPath,
            String storeFolder) {
        return exportExcel(null, excelTitle, excelDatalist, sheetName, rootPath, storeFolder);
    }

    /**
     * 
     * @param excelName
     * @param excelTitle
     * @param excelDatalist
     * @param sheetName
     * @param rootPath
     * @param storeFolder
     * @return
     */
    public static Map<String, String> exportExcel(String excelName, List<List<String>> excelTitle,
            List<List<String[]>> excelDatalist, List<String> sheetName, String rootPath,
            String storeFolder) {
        Map<String, String> exportedExcelPathMap = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat("MMdd_HHmm");
            rootPath = URLDecoder.decode(rootPath + storeFolder + File.separator + OUTPUTDIR
                    + File.separator, "UTF-8");
            String fileName = null;
            if (StringUtils.isNotBlank(excelName)) {
                fileName = URLDecoder.decode(excelName + "_" + df.format(new Date()) + ".xls",
                        "UTF-8");
            } else {
                fileName = URLDecoder.decode(df.format(new Date()) + ".xls", "UTF-8");
            }
            File outputDir = new File(rootPath);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            WritableWorkbook wwb;
            File outputFile = new File(rootPath + fileName);
            wwb = Workbook.createWorkbook(outputFile);
            for (int sheetNumber = 0; sheetNumber < sheetName.size(); ++sheetNumber) {
                WritableSheet sheet = wwb.createSheet(sheetName.get(sheetNumber), sheetNumber);
                Label label;
                WritableCellFormat textFormat = new WritableCellFormat(NumberFormats.TEXT);
                textFormat.setAlignment(Alignment.CENTRE);
                CellView cv = new CellView();
                cv.setFormat(textFormat);
                cv.setAutosize(true);
                for (int i = 0; i < excelTitle.get(sheetNumber).size(); i++) {
                    sheet.setColumnView(i, cv);
                    label = new Label(i, 0, excelTitle.get(sheetNumber).get(i), textFormat);
                    sheet.addCell(label);

                }
                if (excelDatalist.size() > 0) {
                    for (int i = 0; i < excelDatalist.get(sheetNumber).size(); i++) {
                        String[] row = excelDatalist.get(sheetNumber).get(i);
                        int rowLength = row.length;
                        for (int j = 0; j < rowLength; j++) {
                            if (row[j] == null)
                                continue;
                            sheet.setColumnView(i, cv);
                            label = new Label(j, i + 1, row[j], textFormat);
                            sheet.addCell(label);
                        }
                    }
                }
            }
            wwb.write();
            wwb.close();
            exportedExcelPathMap = new HashMap<String, String>();
            exportedExcelPathMap.put("filepath", rootPath);
            exportedExcelPathMap.put("filename", fileName);
            return exportedExcelPathMap;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } finally {

        }
        return exportedExcelPathMap;
    }
    
    public static Map<String, String> exportExcel(String fileName,List<List<String>> excelTitle,
            List<List<String[]>> excelDatalist, List<String> sheetName, String sheetTitle,
            String rootPath, String storeFolder) {
        Map<String, String> exportedExcelPathMap = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat("MMdd_HHmm");
            rootPath = URLDecoder.decode(rootPath + storeFolder + File.separator + OUTPUTDIR
                    + File.separator, "UTF-8");
            if(StringUtils.isBlank(fileName)){
            	fileName = URLDecoder.decode(df.format(new Date()) + ".xls", "UTF-8");
            } 
            File outputDir = new File(rootPath);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            WritableWorkbook wwb;
            File outputFile = new File(rootPath + fileName);
            wwb = Workbook.createWorkbook(outputFile);
            for (int sheetNumber = 0; sheetNumber < sheetName.size(); ++sheetNumber) {
                WritableSheet sheet = wwb.createSheet(sheetName.get(sheetNumber), sheetNumber);
                Label label;
                WritableCellFormat textFormat = new WritableCellFormat(NumberFormats.TEXT);
                textFormat.setAlignment(Alignment.CENTRE);
                CellView cv = new CellView();
                cv.setFormat(textFormat);
                cv.setAutosize(true);

                int cols = excelTitle.get(sheetNumber).size();
                sheet.mergeCells(0, 0, cols - 1, 0);
                label = new Label(0, 0, sheetTitle, textFormat);
                sheet.addCell(label);
                for (int i = 0; i < excelTitle.get(sheetNumber).size(); i++) {
                    sheet.setColumnView(i, cv);
                    label = new Label(i, 1, excelTitle.get(sheetNumber).get(i), textFormat);
                    sheet.addCell(label);
                }
                for (int i = 0; i < excelDatalist.get(sheetNumber).size(); i++) {

                    for (int j = 0; j < excelDatalist.get(sheetNumber).get(i).length; j++) {
                        if (excelDatalist.get(sheetNumber).get(i)[j] == null)
                            continue;
                        //if(!isNumber(excelDatalist.get(sheetNumber).get(i)[j])){
                        sheet.setColumnView(i, cv);
                        label = new Label(j, i + 2, excelDatalist.get(sheetNumber).get(i)[j],
                                textFormat);
                        sheet.addCell(label);
                        //}
                        //else{
                        //jxl.write.Number number = new jxl.write.Number(j,i+1,Double.parseDouble(excelDatalist.get(sheetNumber).get(i)[j])); 
                        //sheet.addCell(number); 
                        //}	  	  
                    }
                }
            }
            wwb.write();
            wwb.close();
            exportedExcelPathMap = new HashMap<String, String>();
            exportedExcelPathMap.put("filepath", rootPath);
            exportedExcelPathMap.put("filename", fileName);
            return exportedExcelPathMap;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } finally {

        }
        return exportedExcelPathMap;
    }

    public static Map<String, String> exportExcel(List<List<String>> excelTitle,
            List<List<String[]>> excelDatalist, List<String> sheetName, String sheetTitle,
            String rootPath, String storeFolder) {
        Map<String, String> exportedExcelPathMap = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat("MMdd_HHmm");
            rootPath = URLDecoder.decode(rootPath + storeFolder + File.separator + OUTPUTDIR
                    + File.separator, "UTF-8");

            String fileName = URLDecoder.decode(df.format(new Date()) + ".xls", "UTF-8");
            File outputDir = new File(rootPath);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            WritableWorkbook wwb;
            File outputFile = new File(rootPath + fileName);
            wwb = Workbook.createWorkbook(outputFile);
            for (int sheetNumber = 0; sheetNumber < sheetName.size(); ++sheetNumber) {
                WritableSheet sheet = wwb.createSheet(sheetName.get(sheetNumber), sheetNumber);
                Label label;
                WritableCellFormat textFormat = new WritableCellFormat(NumberFormats.TEXT);
                textFormat.setAlignment(Alignment.CENTRE);
                CellView cv = new CellView();
                cv.setFormat(textFormat);
                cv.setAutosize(true);

                int cols = excelTitle.get(sheetNumber).size();
                sheet.mergeCells(0, 0, cols - 1, 0);
                label = new Label(0, 0, sheetTitle, textFormat);
                sheet.addCell(label);
                for (int i = 0; i < excelTitle.get(sheetNumber).size(); i++) {
                    sheet.setColumnView(i, cv);
                    label = new Label(i, 1, excelTitle.get(sheetNumber).get(i), textFormat);
                    sheet.addCell(label);
                }
                for (int i = 0; i < excelDatalist.get(sheetNumber).size(); i++) {

                    for (int j = 0; j < excelDatalist.get(sheetNumber).get(i).length; j++) {
                        if (excelDatalist.get(sheetNumber).get(i)[j] == null)
                            continue;
                        //if(!isNumber(excelDatalist.get(sheetNumber).get(i)[j])){
                        sheet.setColumnView(i, cv);
                        label = new Label(j, i + 2, excelDatalist.get(sheetNumber).get(i)[j],
                                textFormat);
                        sheet.addCell(label);
                        //}
                        //else{
                        //jxl.write.Number number = new jxl.write.Number(j,i+1,Double.parseDouble(excelDatalist.get(sheetNumber).get(i)[j])); 
                        //sheet.addCell(number); 
                        //}	  	  
                    }
                }
            }
            wwb.write();
            wwb.close();
            exportedExcelPathMap = new HashMap<String, String>();
            exportedExcelPathMap.put("filepath", rootPath);
            exportedExcelPathMap.put("filename", fileName);
            return exportedExcelPathMap;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } finally {

        }
        return exportedExcelPathMap;
    }

    /**
     * 用于统计时的表格内容导出，需考虑表头中出现的合并情况。这里只考虑一个sheet的情况。
     * @since 2015.12
     * @author cyz
     * @param excelTitle
     * @param excelData
     * @param sheetName
     * @param sheetTitle
     * @param rootPath
     * @param storeFolder
     * @return
     */
    public static Map<String, String> exportExcel(List<List<Map<String, String>>> excelTitle,
            List<String> columnCode, Map<String, Map<String, Object>> excelData, String sheetName,
            String sheetTitle, String rootPath, String storeFolder) {
        Map<String, String> exportedExcelPathMap = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat("MMdd_HHmm");
            rootPath = URLDecoder.decode(rootPath + storeFolder + File.separator + OUTPUTDIR
                    + File.separator, "UTF-8");

            String fileName = URLDecoder.decode(df.format(new Date()) + ".xls", "UTF-8");
            File outputDir = new File(rootPath);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            // 新建excel文件
            File outputFile = new File(rootPath + fileName);
            WritableWorkbook wwb = Workbook.createWorkbook(outputFile);
            //for (int sheetNumber = 0; sheetNumber < sheetName.size(); ++sheetNumber) {
            WritableSheet sheet = wwb.createSheet(sheetName, 0);
            Label label;
            int currentRow = 0;
            List<String> selfFindCode = new ArrayList<String>();
            // 设置单元格格式
            WritableCellFormat textFormat = new WritableCellFormat(NumberFormats.TEXT);
            textFormat.setAlignment(Alignment.CENTRE);
            CellView cv = new CellView();
            cv.setFormat(textFormat);
            cv.setAutosize(true);

            // 处理excel的表头，并写入到文件
            for (int i = 0; i < excelTitle.size(); i++) {
                currentRow = i;
                List<Map<String, String>> titleInARow = excelTitle.get(i);
                int start = 0;
                int prev = 0;
                for (int j = 0; j < titleInARow.size(); j++) {
                    // 这里的map应该是有序的LinkdHashMap，包含span、text、code等内容
                    Map<String, String> title = titleInARow.get(j);
                    int span = Integer.valueOf(title.get("span"));
                    start += prev;
                    Label titleLable = new Label(start, currentRow, title.get("text"), textFormat);
                    sheet.setColumnView(start, cv);
                    sheet.addCell(titleLable);
                    // 合并表头的单元格
                    sheet.mergeCells(start, currentRow, start + span - 1, currentRow);
                    // 赋值prev，用于计算下一个单元格的起始位置
                    prev = span;

                    // 列的code在哪儿给出，直接传参还是放到map里？
                    if (i == excelTitle.size() - 1) {
                        selfFindCode.add(title.get("code"));
                    }
                }
            }
            // 自己查找列代号，以防止额外传递的不一致
            columnCode = selfFindCode;

            // 处理excel的内容，并写入到文件    Map<String, Map<String, Object>> excelData
            for (String key : excelData.keySet()) {
                currentRow++;
                // 把key作为第一列数据填入，key是unitId、discId、provinceId、teacherName等等
                label = new Label(0, currentRow, key, textFormat);
                //sheet.setColumnView(0, cv);
                sheet.addCell(label);
                Map<String, Object> currentRowData = excelData.get(key);
                for (String code : currentRowData.keySet()) {
                    int start = columnCode.indexOf(code) + 1;
                    label = new Label(start, currentRow, currentRowData.get(code).toString(),
                            textFormat);
                    //sheet.setColumnView(start, cv);
                    sheet.addCell(label);
                    // 为空的，即不包括在Map内的不填充即可？
                }
            }

            wwb.write();
            wwb.close();
            exportedExcelPathMap = new HashMap<String, String>();
            exportedExcelPathMap.put("filepath", rootPath);
            exportedExcelPathMap.put("filename", fileName);
            return exportedExcelPathMap;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } finally {

        }
        return exportedExcelPathMap;
    }

    public static File storeMultipartFile(MultipartFile importFile, String storePath,
            PhysicsEnum occasion) throws UnsupportedEncodingException, IOException,
            FileNotFoundException {
        File excelFile = null;
        storePath = URLDecoder.decode(storePath + occasion.getShowing() + File.separator
                + UPLOADDIR + File.separator, "UTF-8");
        File storeDir = new File(storePath);
        if (!storeDir.exists()) {
            storeDir.mkdirs();
        }
        //上传的文件的原始文件名
        String fileName = importFile.getOriginalFilename();
        String storeName = rename(fileName);

        // 读取文件流并保持在指定路径  
        InputStream inputStream = importFile.getInputStream();
        OutputStream outputStream = new FileOutputStream(storePath + storeName);
        byte[] buffer = importFile.getBytes();
        int byteread = 0;
        while ((byteread = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, byteread);
            outputStream.flush();
        }
        outputStream.close();
        inputStream.close();
        //读取excel表格数据并封装
        excelFile = new File(storePath, storeName);
        return excelFile;
    }

    public static List<Map<String, String>> readExcelSheet(int sheetIndex, String tableName,
            Map<String, String> thMap, File excelFile) throws IOException, BiffException, Exception {
        List<Map<String, String>> importDataList = new ArrayList<Map<String, String>>();
        // 获得工作表、表头、行数信息
        // 只能读取xls格式的excel表格，xlsx会出现异常：jxl.read.biff.BiffException: Unable to recognize OLE stream
        Workbook workBook = Workbook.getWorkbook(excelFile);
        Sheet sheet = workBook.getSheets()[sheetIndex];
        Cell[] thCells = sheet.getRow(0);
        int rowsNum = sheet.getRows();
        if (!tableName.equals(sheet.getName())) {
            throw new Exception("excel中的工作表名与目标表名未对应，无法读取数据！");
        }
        // 遍历所有行
        for (int i = 1; i < rowsNum; i++) {
            if (isEmptyRow(sheet.getRow(i)) || isExcelEmptyRow(sheet.getRow(i), thMap.size()))
                continue;
            // excel中的行数据转换为map数据
            HashMap<String, String> rowMap = new HashMap<String, String>();
            Cell[] rowData = sheet.getRow(i);
            for (int j = 0, k = 0; (j < sheet.getRow(0).length) && (k < thMap.size()); j++) {
                String thName = StringDealUtil.removeBeforeAndAfterBlank(thCells[j].getContents());
                if (thMap.containsKey(thName)) {//表头是数据对应的唯一标识
                    if ((rowData.length - 1) < j || rowData[j].getContents().equals(""))
                        rowMap.put(thMap.get(thName), "");
                    else {
                        rowMap.put(thMap.get(thName), StringDealUtil
                                .removeBeforeAndAfterBlank((rowData[j].getContents())));
                    }
                    k++;//读取了一列数据，计数器加1
                }
            }
            importDataList.add(rowMap);
        }
        return importDataList;
    }

    public static String importFile(MultipartFile importFile, String storePath) throws Exception {
        if (!importFile.isEmpty()) {
            String fileName = importFile.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf('.') + 1).toUpperCase();
            if (suffix.equals("ZIP")) {
                storePath = URLDecoder.decode(storePath + "collectZip" + File.separator + UPLOADDIR
                        + File.separator, "UTF-8");
            } else if (suffix.equals("RAR")) {
                storePath = URLDecoder.decode(storePath + "collectRar" + File.separator + UPLOADDIR
                        + File.separator, "UTF-8");
            } else {
                System.out.println("FILETYPE WRONG!");
                return null;
            }

            File storeDir = new File(storePath);
            if (!storeDir.exists()) {
                storeDir.mkdirs();
            }

            String storeName = rename(fileName);
            InputStream inputStream = importFile.getInputStream();
            OutputStream outputStream = new FileOutputStream(storePath + storeName);
            byte[] buffer = importFile.getBytes();
            int byteread = 0;
            while ((byteread = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, byteread);
                outputStream.flush();
            }
            outputStream.close();
            inputStream.close();

            return storePath + storeName;
        }
        return null;
    }

    private static String rename(String originalName) {
        String storeName = null;
        Long now = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        Long random = (long) (Math.random() * now);
        storeName = now + "" + random;
        if (originalName.indexOf(".") != -1) {
            storeName += originalName.substring(originalName.lastIndexOf("."));
        }

        return storeName;
    }

    /**
     * 判断一行是否为空
     * @param cells
     * @param colsNum
     * @return 如果空返回true
     */
    private static boolean isExcelEmptyRow(Cell[] cells, int colsNum) {
        boolean result = true;
        for (int i = 0; i < colsNum; i++) {
            if (!StringDealUtil.removeBeforeAndAfterBlank(cells[i].getContents()).equals("")) {
                result = false;
                break;
            }
        }
        return result;
    }

    private static Boolean isEmptyRow(Cell[] cells) {
        boolean flag = true;
        int i = 0;
        while (flag && (i < cells.length)) {
            if (!cells[i].getContents().trim().equals(""))
                flag = false;
            i++;
        }
        return flag;
    }

    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }

    /***
     * 生成excel导入文件的模板
     * @param sheetName
     *        模板文件的文件名和sheet名
     * @param colData
     *        列数据，包括下拉框选项
     * @param filePath
     *        文件存储目录
     * @param excel 
     * @return
     */
    public static Map<String, String> createExcelTemplate(String fileName, String sheetName,
            LinkedHashMap<String, List<Object>> colData, String filePath, PhysicsEnum excel, List<String> sampleDatas) {
        HashMap<String, String> resault = new HashMap<String, String>();
        int MAX_COL = 100;
        try {
            filePath = URLDecoder.decode(filePath + excel.getShowing() + File.separator, "UTF-8");
            fileName = URLDecoder.decode(fileName + ".xls", "UTF-8");
            File outputDir = new File(filePath);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            WritableWorkbook wwb;
            File outputFile = new File(filePath, fileName);
            if (!outputFile.exists()) {
                wwb = Workbook.createWorkbook(outputFile);
                WritableSheet sheet = wwb.createSheet(sheetName, 0);
                Label label;
                WritableCellFormat textFormat = new WritableCellFormat(NumberFormats.TEXT);
                textFormat.setAlignment(Alignment.CENTRE);
                CellView cv = new CellView();
                cv.setFormat(textFormat);
                cv.setAutosize(true);

                //向模板中写入数据
                Iterator<Map.Entry<String, List<Object>>> iter = colData.entrySet().iterator();
                int i = 0;
                List<Object> selectList;
                while (iter.hasNext()) {
                    Map.Entry<String, List<Object>> entry = (Map.Entry<String, List<Object>>) iter
                            .next();
                    sheet.setColumnView(i, cv);
                    label = new Label(i, 0, entry.getKey().trim(), textFormat);
                    selectList = entry.getValue();
                    if (selectList != null) {
                        WritableCellFeatures wcf = new WritableCellFeatures();
                        wcf.setDataValidationList(selectList);
                        label.setCellFeatures(wcf);
                    }
                    sheet.addCell(label);
                    if (selectList != null)
                        sheet.applySharedDataValidation(label, 0, MAX_COL);
                    i++;
                }
                i = 0;
                for (String sample : sampleDatas) {
                    sheet.setColumnView(i, cv);
                    label = new Label(i, 1, sample, textFormat);
                    sheet.addCell(label);
                    i++;
                }
                wwb.write();
                wwb.close();
            }
            resault.put("filePath", filePath);
            resault.put("fileName", fileName);
            return resault;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } finally {

        }
        return resault;
    }

    //读取excel表格测试
    public static void main(String[] args) throws Exception {
        //readExcel("");
    }

    public static String readHtml(String path) throws Exception {
        Logger logger = getCurrentLogger();
        logger.warn("path : " + path);
        if (ifFileExist(path)) {
            logger.warn("html is existed!");
            File file = new File(path);
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), "utf-8");//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            StringBuilder htmlTxt = new StringBuilder("");
            String tempTxt = null;
            while ((tempTxt = bufferedReader.readLine()) != null) {
                htmlTxt.append(tempTxt);
            }
            read.close();
            logger.warn("html content is : " + htmlTxt.toString());
            return replaceChars(htmlTxt.toString());
        } else {
            logger.warn("html is not existed!");
            return "";
        }
    }

    /**
     * 对于富文本需要对已经转化的字符进行还原
     * @param value
     * @return
     */
    private static String replaceChars(String value) {
        value = value.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
        value = value.replaceAll("&#40;", "\\(").replaceAll("&#41;", "\\)");
        value = value.replaceAll("&#39;", "'");
        //value = value.replaceAll("","eval\\((.*)\\)");
        value = value.replaceAll("\"\"", "[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']");
        value = value.replaceAll("script", "");
        return value;
    }

    /**
     * 通过路径和内容生成html
     * @param path
     * @param content
     * @return
     */
    public static String writeHtml(String fileDirectory, String fileName, String content) {
        File directory = new File(fileDirectory);//先检查目录是否存在
        boolean isExist = directory.exists();
        if (!isExist) {
            isExist = directory.mkdir();//建目录
        }
        String directoryAndFile = fileDirectory + fileName;
        OutputStreamWriter out = null;
        try {
            out = new OutputStreamWriter(new FileOutputStream(directoryAndFile), "UTF-8");
            out.write(content);
            out.flush();
            out.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return directoryAndFile;
    }
    /**
     * 判断上传附件的页数限制
     * @param file
     * @param occ
     * @return
     * @throws IOException
     */
    public static boolean chackPageNumbers(MultipartFile file, FileOccasion occ) throws IOException {
        PdfReader reader = new PdfReader(file.getBytes());
        int pages = reader.getNumberOfPages();
        long size = 5242880;
        return pages <= size;
    }
}
