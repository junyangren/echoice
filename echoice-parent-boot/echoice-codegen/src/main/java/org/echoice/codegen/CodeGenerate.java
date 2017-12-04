package org.echoice.codegen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import freemarker.template.Template;

public class CodeGenerate {
	private String author = "echoice";
    private String currentDate = "2017/11/20";
    private String tablePrefix = "ec_";
    private String tableName = "ec_result_code_cfg";//sm_device_info
    private String packageName = "com.zzxipd.smartwear";
    private String tableAnnotation = "结果码配置";//设备管理
    private String url = "jdbc:mysql://192.168.21.44/smartwear_db?useUnicode=true&characterEncoding=utf-8";
    private String user = "smartwearadmin";
    private String password = "smartwearadmin123";
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private String diskPath = "D://codeGenerate//";
    private String changeTableName = replaceUnderLineAndUpperCase(tableName.substring(tablePrefix.length()));

    private final Pattern commonChinese = Pattern.compile("^[\u4e00-\u9fa5_a-zA-Z0-9]+");
    private ColumnClass columnClassPk;
    private List<ColumnClass> columnClassList;
    
    
    public Connection getConnection() throws Exception{
        Class.forName(DRIVER);
        Connection connection= DriverManager.getConnection(url, user, password);
        return connection;
    }

    public CodeGenerate(){
    	this.currentDate=DateFormatUtils.format(new Date(), "yyyy/MM/dd");
    }

    public CodeGenerate(CodeGenBean codeGenBean){
    	super();
    	this.author=codeGenBean.getAuthor();
    	this.tablePrefix=codeGenBean.getDbTablePrefix();
    	this.tableName=codeGenBean.getDbTable();
    	this.tableAnnotation=codeGenBean.getDbTableNameCn();
    	this.packageName=codeGenBean.getAppPackageName();
    	this.url="jdbc:mysql://"+codeGenBean.getDbIp()+"/"+codeGenBean.getDbName()+"?useUnicode=true&characterEncoding=utf-8";
    	this.user=codeGenBean.getDbUser();
    	this.password=codeGenBean.getDbPasswd();
    }

    public void generate() throws Exception{
    	Connection connection=null;
        try {
            connection = getConnection();
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSetPk = databaseMetaData.getPrimaryKeys(null, "%", tableName);
            ResultSet resultSet = databaseMetaData.getColumns(null,"%", tableName,"%");
            
            this.columnClassPk = generateColumnClassPK(resultSetPk);
            this.columnClassList=generateColumnClassList(resultSet,columnClassPk);
            //生成Mapper文件
            //generateMapperFile(resultSet);
            //生成Dao文件
            generateDaoFile();
            generateDaoImplFile();
            //生成Repository文件
            //generateRepositoryFile(resultSet);
            //生成服务层接口文件
            generateServiceInterfaceFile();
            //生成服务实现层文件
            generateServiceImplFile();
            //生成Controller层文件
            generateControllerFile();
            //生成DTO文件
            //generateDTOFile(resultSet);
            //生成Model文件
            generateModelFile();
            //生成listjsp文件
            generateJspListFile();
            //生成formjsp文件
            generateJspFormFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{
        	if(connection!=null){
        		connection.close();
        	}
        }
    }

    private void generateModelFile() throws Exception{

        final String suffix = ".java";
        final String path = diskPath + changeTableName + suffix;
        final String templateName = "Model.ftl";
        File mapperFile = new File(path);
        Map<String,Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName,mapperFile,dataMap);

    }
    
    private ColumnClass generateColumnClassPK(ResultSet resultSetPk) throws Exception{
    	ColumnClass columnClassPk = null;
        while(resultSetPk.next()){
        	columnClassPk=mapper(resultSetPk,true);
        	break;
        }
        return columnClassPk;
    }
    
    private List<ColumnClass> generateColumnClassList(ResultSet resultSet,ColumnClass pk) throws Exception{
    	List<ColumnClass> columnClassList = new ArrayList<>();
        ColumnClass columnClass = null;        
        while(resultSet.next()){
            columnClass = mapper(resultSet);
            if(columnClass.getColumnName().equalsIgnoreCase(pk.getColumnName())){
            	columnClass.setPk(true);
            	pk.setJavaType(columnClass.getJavaType());
            	pk.setColumnType(columnClass.getColumnType());
            	pk.setColumnComment(columnClass.getColumnComment());
            	pk.setColumnShortComment(columnClass.getColumnShortComment());
            	pk.setRequired(true);
        	}
            columnClassList.add(columnClass);
        }
        return columnClassList;
    }
    
    private ColumnClass mapper(ResultSet resultSet) throws Exception{
    	return mapper(resultSet,false);
    }
    
    private ColumnClass mapper(ResultSet resultSet,boolean rsPk) throws Exception{
    	ColumnClass columnClass = new ColumnClass();
        //获取字段名称
        columnClass.setColumnName(resultSet.getString("COLUMN_NAME"));
        //转换字段名称，如 sys_name 变成 SysName
        columnClass.setChangeColumnName(replaceUnderLineAndUpperCase(columnClass.getColumnName()));
        if(!rsPk){
            //获取字段类型
            columnClass.setColumnType(resultSet.getString("TYPE_NAME"));
            //字段对应的java类型
            if(columnClass.getColumnType().equalsIgnoreCase("DATETIME")||columnClass.getColumnType().equalsIgnoreCase("DATE")){
            	columnClass.setJavaType("Date");
            }else if(columnClass.getColumnType().equalsIgnoreCase("TINYINT")||columnClass.getColumnType().equalsIgnoreCase("MEDIUMINT")
            		||columnClass.getColumnType().equalsIgnoreCase("SMALLINT")||columnClass.getColumnType().equalsIgnoreCase("INT")){
            	columnClass.setJavaType("Integer");
            }else if(columnClass.getColumnType().equalsIgnoreCase("BIGINT")){
            	columnClass.setJavaType("Long");
            }else if(columnClass.getColumnType().equalsIgnoreCase("FLOAT")||columnClass.getColumnType().equalsIgnoreCase("DOUBLE")
            		||columnClass.getColumnType().equalsIgnoreCase("DECIMAL")){
            	columnClass.setJavaType("Double");
            }else{
            	if(columnClass.getColumnType().equalsIgnoreCase("TEXT")){
            		columnClass.setFormType("textarea");
            	}
            	columnClass.setJavaType("String");
            }
            //字段在数据库的注释
            columnClass.setColumnComment(resultSet.getString("REMARKS"));
            Matcher matcher=commonChinese.matcher(columnClass.getColumnComment());
            if(matcher.find()){
            	columnClass.setColumnShortComment(matcher.group());
            }else{
            	columnClass.setColumnShortComment(columnClass.getColumnComment());
            }
            //得到选择列表数据
            System.out.println("getColumnComment:"+columnClass.getColumnComment());
            String sel1Arr[]=StringUtils.splitByWholeSeparator(columnClass.getColumnComment(),":");
            if(sel1Arr.length==2){
            	String sel2Arr[]=StringUtils.splitByWholeSeparator(sel1Arr[1],";");
            	for (String selOption : sel2Arr) {
            		String selOptionArr[]=StringUtils.splitByWholeSeparator(selOption,",");
            		columnClass.addOption(selOptionArr[1], selOptionArr[0]);
				}
            	System.out.println("Options size:"+columnClass.getOptions().size());
            }
            //必填
            columnClass.setColumnSize(resultSet.getInt("COLUMN_SIZE"));
            String isNullable= resultSet.getString("IS_NULLABLE");
            if("NO".equalsIgnoreCase(isNullable)){
            	columnClass.setRequired(true);
            }
        }
        return columnClass;
    }

    private void generateDTOFile(ResultSet resultSet) throws Exception{
        final String suffix = "DTO.java";
        final String path = diskPath + changeTableName + suffix;
        final String templateName = "DTO.ftl";
        File mapperFile = new File(path);
        Map<String,Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName,mapperFile,dataMap);
    }

    private void generateControllerFile() throws Exception{
        final String suffix = "Controller.java";
        final String path = diskPath + changeTableName + suffix;
        final String templateName = "Controller.ftl";
        File mapperFile = new File(path);
        Map<String,Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName,mapperFile,dataMap);
    }

    private void generateServiceImplFile() throws Exception{
        final String suffix = "ServiceImpl.java";
        final String path = diskPath + changeTableName + suffix;
        final String templateName = "ServiceImpl.ftl";
        File mapperFile = new File(path);
        Map<String,Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName,mapperFile,dataMap);
    }

    private void generateServiceInterfaceFile() throws Exception{
        final String prefix = "";
        final String suffix = "Service.java";
        final String path = diskPath + prefix + changeTableName + suffix;
        final String templateName = "ServiceInterface.ftl";
        File mapperFile = new File(path);
        Map<String,Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName,mapperFile,dataMap);
    }

    private void generateRepositoryFile(ResultSet resultSet) throws Exception{
        final String suffix = "Repository.java";
        final String path = diskPath + changeTableName + suffix;
        final String templateName = "Repository.ftl";
        File mapperFile = new File(path);
        Map<String,Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName,mapperFile,dataMap);
    }

    private void generateDaoFile() throws Exception{
        final String suffix = "Dao.java";
        final String path = diskPath + changeTableName + suffix;
        final String templateName = "Dao.ftl";
        File mapperFile = new File(path);
        Map<String,Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName,mapperFile,dataMap);
    }
    
    private void generateDaoImplFile() throws Exception{
        final String suffix = "DaoImpl.java";
        final String path = diskPath + changeTableName + suffix;
        final String templateName = "DaoImpl.ftl";
        File mapperFile = new File(path);
        Map<String,Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName,mapperFile,dataMap);
    }
    
    private void generateJspListFile() throws Exception{
        final String suffix = "List.jsp";
        final String path = diskPath + StringUtils.uncapitalize(changeTableName) + suffix;
        final String templateName = "jspList.ftl";
        File mapperFile = new File(path);
        Map<String,Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName,mapperFile,dataMap);
    }
    
    private void generateJspFormFile() throws Exception{
        final String suffix = "Form.jsp";
        final String path = diskPath + StringUtils.uncapitalize(changeTableName) + suffix;
        final String templateName = "jspForm.ftl";
        File mapperFile = new File(path);
        Map<String,Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName,mapperFile,dataMap);
    }

    private void generateMapperFile() throws Exception{
        final String suffix = "Mapper.xml";
        final String path = diskPath + changeTableName + suffix;
        final String templateName = "Mapper.ftl";
        File mapperFile = new File(path);
        Map<String,Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName,mapperFile,dataMap);

    }

    private void generateFileByTemplate(final String templateName,File file,Map<String,Object> dataMap) throws Exception{
        Template template = FreeMarkerTemplateUtils.getTemplate(templateName);
        FileOutputStream fos = new FileOutputStream(file);
        dataMap.put("table_name_small",tableName);
        dataMap.put("table_name",changeTableName);
        dataMap.put("author",this.author);
        dataMap.put("date",currentDate);
        dataMap.put("package_name",packageName);
        dataMap.put("table_annotation",tableAnnotation);
        dataMap.put("pkColumn", columnClassPk);
        dataMap.put("model_column",columnClassList);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"),10240);
        template.process(dataMap,out);
    }

    public String replaceUnderLineAndUpperCase(String str){
        StringBuffer sb = new StringBuffer();
        sb.append(str);
        int count = sb.indexOf("_");
        while(count!=0){
            int num = sb.indexOf("_",count);
            count = num + 1;
            if(num != -1){
                char ss = sb.charAt(count);
                char ia = (char) (ss - 32);
                sb.replace(count , count + 1,ia + "");
            }
        }
        String result = sb.toString().replaceAll("_","");
        return StringUtils.capitalize(result);
    }
}
