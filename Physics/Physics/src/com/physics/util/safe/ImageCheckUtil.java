package com.physics.util.safe;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;



public class ImageCheckUtil {
	
	private static Map<ImageType,String> imageCodes; 
	private static enum ImageType{BMP,GIF,
		EPS,DCS,JPEG,JPG,JPE,PCX,
		PDF,RAW,PICT,PXR,PNG,SCT,
		TIFF,TARGA;
		//包含在内的格式返回true
		private static boolean contains(String imageType){
			if(StringUtils.isBlank(imageType)) return false;
			for(ImageType type:ImageType.values()){
				if(type.toString().equals(imageType.toUpperCase()))
					return true;
			}
			return false;
		
		}
	}
	//对图片的安全检查
		public static boolean ImageSafeCheck(MultipartFile image) {
			
			try {
				return (checkSize(image)&&checkFrontChar(image));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
		/**
		 * 检查图片类型
		 * @param image
		 * @return
		 */
		public static String checkMimeType(MultipartFile image){
			if(image == null){
				return null;
			}
			String fileName = image.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf('.') + 1);
			if(ImageType.contains(suffix))
				return suffix;
			return null;
		}
		/**
		 * 重写图片，添加水印
		 * @param file
		 * @return
		 * @throws IOException 
		 */
		public static void rewriteImage(File image,String fileType) throws IOException{
			
			Image img = ImageIO.read(image);
			int width = img.getWidth(null);
			int height = img.getHeight(null);
			BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufferedImage.createGraphics();
			g.drawImage(img, 0, 0, width, height, null);
			
			String waterMarkContent = "学科自检平台";
			
			g.setColor(Color.BLUE);
			g.setFont(new Font("宋体",Font.PLAIN,12));
			
			int waterMarkLength = g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(),0,waterMarkContent.length());
			
			int x = width - waterMarkLength - 10;
			int y = height - 20;
			
			g.drawString(waterMarkContent, x, y);
			g.dispose();
			ImageIO.write(bufferedImage,fileType,image);
		}
		
	private static void initImageCodes(){
				//BMP 424d,
				//GIF 4749 4638,
				//EPS ,DCS,
				//JPEG ffd8 ffe0,
				//JPE,PCX,
				//FFD8 = JPG
				//PDF 2550 4446,RAW,PICT,PXR,
				//PNG 8950 4e47,SCT,
				//TIFF 4d4d 002a OR 4949 2A00
				//TARGA xx xx xx;
		synchronized (ImageType.BMP){
			if(imageCodes==null){
					imageCodes = new HashMap<ImageType,String>(0);
					imageCodes.put(ImageType.BMP, "424d");
					imageCodes.put(ImageType.GIF, "4749");
					imageCodes.put(ImageType.JPEG, "ffd8");
					imageCodes.put(ImageType.JPG, "ffd8");
					imageCodes.put(ImageType.PDF, "2550");
					imageCodes.put(ImageType.PNG, "8950");
					imageCodes.put(ImageType.TIFF, "4d4d");
			}
		}
	}
	private static String getImageCode(ImageType type){
		if(type == null) return "";
		if(imageCodes==null) 
			initImageCodes();
		return imageCodes.get(type);
	}
	
	/**
	 * 检查图片大小
	 * @param image
	 * @return
	 * @throws IOException 
	 */
	private static boolean checkSize(MultipartFile image) throws IOException{
		if(image == null) {
			return false;
		}
		
		CommonsMultipartFile cf = (CommonsMultipartFile) image;
		DiskFileItem df = (DiskFileItem)cf.getFileItem();
		Image img = ImageIO.read(df.getStoreLocation());
		
		if(img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0)
			return false;
		
		return true;
	}
	/**
	 * 检查图片的开头字符
	 * @return
	 * @throws IOException 
	 */
	private static boolean checkFrontChar(MultipartFile image) throws IOException{
		if(image == null){
			return false;
		}
		
		CommonsMultipartFile cf = (CommonsMultipartFile) image;
		DiskFileItem df = (DiskFileItem)cf.getFileItem();
		File file = df.getStoreLocation();
		
		InputStream is = new FileInputStream(file);
		byte[] bt = new byte[2];
		is.read(bt);
		String magicNumberString = bytesToHexString(bt);
		ImageType type1 = null;
		
		String fileName = image.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf('.') + 1);
		for(ImageType type:ImageType.values()){
			if(type.toString().equals(suffix.toUpperCase()))
				type1 = type;
		}
		String imageCode = getImageCode(type1);
		if(imageCode.equals(magicNumberString))
			return true;
		return false;
	}
	/**
	 * 把图片前几个字符修改成字符串
	 * @param src
	 * @return
	 */
	private static String bytesToHexString(byte[] src){
		StringBuilder stringBuilder = new StringBuilder();
		if(src == null || src.length <= 0) {
			return null;
		}
		
		for(int i=0;i<src.length;i++){
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if(hv.length() < 2){
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
	
}
