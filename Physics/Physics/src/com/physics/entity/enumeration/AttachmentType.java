package com.physics.entity.enumeration;

public class AttachmentType extends EnumModule{

	public static final PhysicsEnum COLLECTION= new PhysicsEnum("1","COLLECTION");
	public static final PhysicsEnum VIDEO= new PhysicsEnum("2","VIDEO");
	public static final PhysicsEnum NEWS = new PhysicsEnum("3","NEWS");
	public static final PhysicsEnum TEACHER = new PhysicsEnum("4","TEACHER");
	public static final PhysicsEnum FEEDBACK = new PhysicsEnum("5","FEEDBACK");
	public static final PhysicsEnum TEMPLATE = new PhysicsEnum("6", "TEMPLATE");
	public static final PhysicsEnum SURVEY = new PhysicsEnum("7", "SURVEY");
	public static final PhysicsEnum BACKGROUND_DOCUMENT = new PhysicsEnum("0", "BACKGROUND_DOCUMENT");
	public static final PhysicsEnum PROJECT = new PhysicsEnum("8","PROJECT");
	public static final PhysicsEnum BRIEF = new PhysicsEnum("9","BRIEF");
	public static final PhysicsEnum HTML = new PhysicsEnum("10","ONLINEEDIT");
	public static final PhysicsEnum EXCEL = new PhysicsEnum("11","EXCEL");
	
	public AttachmentType() {
		setTypeMaterial();
	}

	
	@Override
	protected void setTypeMaterial() {
		
		this.typeMaterial = new PhysicsEnum[] { COLLECTION, VIDEO, 
				NEWS, TEACHER,FEEDBACK,TEMPLATE,SURVEY,
				BACKGROUND_DOCUMENT,PROJECT,BRIEF,HTML};
		
	}

}
