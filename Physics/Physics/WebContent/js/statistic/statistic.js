var statisticConfig = function(){
    	this.groups;
    	this.setGroups=function(groups){
    		this.groups = groups;
    	};
    };
    var gatherGroup = function(){
	this.entityId;
	this.asc;
	this.propName;
	this.attrs;
	this.setEntityId=function(entityId){
		this.entityId=entityId;
	};
	this.addAttrs=function(gatherAttr){
		this.attrs.push(gatherAttr);
	};
	this.construct=function(entityId){
		this.setEntityId(entityId);
		this.attrs = new Array();
	};
	this.setPropName=function(propName){
		this.propName = propName;
	};
	this.setAsc=function(asc){
		this.asc=asc;
	};
};

var gatherAttr=function(){
	this.name;
	this.lowValue;
	this.operType="";
	this.value="";
	this.dataType="";
	this.highValue;
	this.setName=function(name){
		this.name=name;
	};
	this.setLimitation=function(lowValue,highValue){
		this.lowValue=lowValue;
		this.highValue=highValue;
	};
	this.contruct1=function(name,dataType,value,operType){
		this.dataType = dataType;
		this.setName(name);
		this.value=value;
		this.operType = operType;
	};
	this.contruct2=function(name,dataType,lowValue,highValue,operType){
		this.dataType = dataType;
		this.setName(name);
		this.operType = operType;
		this.setLimitation(lowValue,highValue);
	};
};