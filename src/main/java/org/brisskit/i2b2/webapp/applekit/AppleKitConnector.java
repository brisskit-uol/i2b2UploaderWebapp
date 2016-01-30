package org.brisskit.i2b2.webapp.applekit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AppleKitConnector {

	 List<String> fileList;
	    private static final String INPUT_ZIP_FILE = "C:\\1Brisskit\\saj_export.zip";
	    private static final String OUTPUT_FOLDER = "C:\\outputzip";
	 
	    
	    public static void createworkbook(HSSFWorkbook workbook, String name)
		{
			 try {
		            FileOutputStream out =
		                    new FileOutputStream(new File("C:\\outputzip\\"+ name +".xls"));
		            workbook.write(out);
		            out.close();
		            System.out.println("Excel written successfully..");
		             
		        } catch (FileNotFoundException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		}
	    
	    public static void populate(HSSFWorkbook workbook, HSSFSheet sheet2, Map<Integer, Object[]> data2)
		{
			CreationHelper createHelper = workbook.getCreationHelper();    
			HSSFCellStyle my_style_0 = workbook.createCellStyle();
			my_style_0.setDataFormat(createHelper.createDataFormat().getFormat("dd-mm-yyyy HH:MM:SS"));
				
			Set<Integer> keyset = data2.keySet();
	        int rownum = 0;
	        for (Integer key : keyset) {
	        	System.out.println(key);
	            Row row = sheet2.createRow(rownum++);
	            Object [] objArr = data2.get(key);
	            int cellnum = 0;
	            for (Object obj : objArr) {
	                Cell cell = row.createCell(cellnum++);
	                if(obj instanceof Date) {
	                	cell.setCellStyle(my_style_0); cell.setCellValue((Date)obj);
	                     }
	                else if(obj instanceof Boolean)
	                    cell.setCellValue((Boolean)obj);
	                else if(obj instanceof String)
	                    cell.setCellValue((String)obj);
	                else if(obj instanceof Double)
	                    cell.setCellValue((Double)obj);
	                else if(obj == null)
	                    cell.setCellValue("null");
	            }
	        }
		}
	    
	    public static void main( String[] args )
	    {    	
	    	unZipIt(INPUT_ZIP_FILE,OUTPUT_FOLDER);
	    	
	    	try {
	    		
	    		
    			
	    	File fXmlFile = new File("C:\\outputzip\\export.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		 
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
		 
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		 
			NodeList nList = doc.getElementsByTagName("Record");
			
			Set set = new LinkedHashSet();
			Set set_fields = new LinkedHashSet();
			String identifier = "";
			//Object data_headings[] = new Object[fields.length];
			for (int temp = 0; temp < nList.getLength(); temp++) {
				 
				Node nNode = nList.item(temp);
				
				Attr attr = (Attr) nNode.getAttributes().getNamedItem("unit");
                String unit = "null";
                if (attr != null) {
                    unit= attr.getValue();   
                    if (unit.equals("")) { unit = "unknown"; }
                    //System.out.println("unit: " + unit);                      
                }
                
				attr = (Attr) nNode.getAttributes().getNamedItem("type");
                String type = "null";
                if (attr != null) {
                    type = attr.getValue();                      
                    set.add(type+","+unit); 
                    set_fields.add(type); 
                    System.out.println("type: " + type + " unit: " + unit); 
                }
                
                attr = (Attr) nNode.getAttributes().getNamedItem("source");
                //String source = "null";
                if (attr != null) {
                	identifier= attr.getValue(); 
                	identifier = identifier.replaceAll("\\W", "");
                	//identifier = identifier.substring(0, 8);
                    //System.out.println("source: " + source);                      
                }
			}
			
			//iterate set
			Object data_headings_1[] = new Object[set.size()];
			Object data_headings_2[] = new Object[set.size()];
			Object data_headings_3[] = new Object[set.size()];
			
			int data_count = 0;
			for (Object s : set) {
			    String parts[]= String.valueOf(s).split(",");
			    data_headings_1[data_count] = parts[0];
			    data_headings_2[data_count] = parts[0];
			    data_headings_3[data_count] = parts[0] + "[" + parts[1] + "]";
			    data_count++;
			}
            		
			System.out.println(set);
			System.out.println(data_headings_1.toString());
			System.out.println(data_headings_2.toString());
			System.out.println(data_headings_3.toString());
			
			HSSFWorkbook workbook = new HSSFWorkbook();				
			HSSFSheet sheet = workbook.createSheet("DATA");
			Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
			
			Object[] one = ArrayUtils.addAll(new Object[] {"ID", "OBS_START_DATE", "AGE", "GENDER", "IPHONE"}, data_headings_1);
			Object[] two = ArrayUtils.addAll(new Object[] {"null", "null"          , "Age", "Sex", "IPHONE"}, data_headings_2);
			Object[] three = ArrayUtils.addAll(new Object[] {"null", "null"          , "age", "sex", "iphone[text]"}, data_headings_3);
			
			//data.put(1, new Object[] {"ID"  , "OBS_START_DATE", "AGE", "GENDER", "HEIGHT", "BODYMASS", "STEPCOUNT", "DISTANCEWALKINGRUNNING", "FLIGHTSCLIMBED"});
			//data.put(2, new Object[] {"null", "null"          , "Age", "Sex"   , "Height", "BodyMass", "StepCount", "DistanceWalkingRunning", "FlightsClimbed"});
			//data.put(3, new Object[] {"null", "null"          , "age", "sex"   , "Height[m]", "BodyMass[kg]", "StepCount[count]", "DistanceWalkingRunning[m]", "FlightsClimbed[count]"});
			data.put(1,one);
			data.put(2,two);
			data.put(3,three);
			
			//String fields[] = {"HKQuantityTypeIdentifierHeight", "HKQuantityTypeIdentifierBodyMass", "HKQuantityTypeIdentifierStepCount", "HKQuantityTypeIdentifierDistanceWalkingRunning", "HKQuantityTypeIdentifierFlightsClimbed"};
			String[] fields = (String[]) set_fields.toArray(new String[set_fields.size()]);
			for (String s: fields) {           
			    //Do your stuff here
			    System.out.println(s); 
			}
			for (int temp = 0; temp < nList.getLength(); temp++) {
				 
				Node nNode = nList.item(temp);
		 
				//System.out.println("\nCurrent Element :" + nNode.getNodeName());
				
				/*if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					
					System.out.println("ns1:REFERENCE : " +eElement.getElementsByTagName("ns1:REFERENCE").item(0).getTextContent());
				}*/
				
                if (nNode.hasAttributes()) {
                	
                	int index = -1;
                	
                    Attr attr = (Attr) nNode.getAttributes().getNamedItem("type");
                    String type = "null";
                    if (attr != null) {
                        type = attr.getValue();                      
                         
                        index = Arrays.asList(fields).indexOf(type);
                        //System.out.println("type: " + type + " index: " + index); 
                    }
                    attr = (Attr) nNode.getAttributes().getNamedItem("source");
                    String source = "null";
                    if (attr != null) {
                        source= attr.getValue();                      
                        //System.out.println("source: " + source);                      
                    }
                    attr = (Attr) nNode.getAttributes().getNamedItem("unit");
                    String unit = "null";
                    if (attr != null) {
                        unit= attr.getValue();                      
                        //System.out.println("unit: " + unit);                      
                    }
                    attr = (Attr) nNode.getAttributes().getNamedItem("startDate");
                    String startDate = "null";
                    Date startDate_date = null; // 20150421112600+0100

                    if (attr != null) {
                        startDate= attr.getValue();                
                        
                        //startDate_date = new SimpleDateFormat("yyyyMMddHHmmssSSSZ").parse(startDate);
                        startDate_date = new SimpleDateFormat("yyyyMMddHHmmss").parse(startDate);
						
                        //System.out.println("startDate: " + startDate);                      
                    }
                    attr = (Attr) nNode.getAttributes().getNamedItem("endDate");
                    String endDate = "null";
                    if (attr != null) {
                        endDate= attr.getValue();                      
                        //System.out.println("endDate: " + endDate);                      
                    }
                    attr = (Attr) nNode.getAttributes().getNamedItem("value");
                    Double value = (double) 0;
                    if (attr != null) {
                        value= Double.valueOf(attr.getValue());                      
                        //System.out.println("value: " + value);                      
                    }
                    attr = (Attr) nNode.getAttributes().getNamedItem("recordCount");
                    Double recordCount = null;
                    if (attr != null) {
                        recordCount= Double.valueOf(attr.getValue());                      
                        //System.out.println("recordCount: " + recordCount);                      
                    }
                    
                    int t = temp + 4;
                    
                    
                    
                    Object populate_data[] = new Object[fields.length];
                    for (int j = 0; j < fields.length; j++) {
                    	populate_data[j] = Double.valueOf(0);     
                    	
                    	if (j == index) { populate_data[j] = value; }
                    }
                    Double age = (double) 1;
                    
                    Object[] both = ArrayUtils.addAll(new Object[] {"2", startDate_date, age, "U", identifier}, populate_data);
                    
                    //String obj = " \"1\",\"" + startDate + "\",\"age\",\"sex\"," + populate_data.toString();
                    
                    //System.out.println(obj);
                    
        		   //data.put(t, new Object[] {obj});	
        		   
        		    data.put(t, both);	
        		   
                    /*
                    data.put(t, new Object[] {"1",
                    	  startDate,
  						  "age",
  						  "sex",
  						  "HKQuantityTypeIdentifierHeight",
  					 	  "HKQuantityTypeIdentifierBodyMass",
  					 	  "HKQuantityTypeIdentifierStepCount",
  					 	  "HKQuantityTypeIdentifierDistanceWalkingRunning",
  					 	  "HKQuantityTypeIdentifierFlightsClimbed"});
  					*/
                    
                }
		 
			}
			populate(workbook,sheet, data);		
			createworkbook(workbook,"test1");
			
			
	    	} catch (Exception e) {
				e.printStackTrace();
			    }
	    	
	    }
	 
	    /**
	     * Unzip it
	     * @param zipFile input zip file
	     * @param output zip file output folder
	     */
	    static public void unZipIt(String zipFile, String outputFolder){
	 
	     byte[] buffer = new byte[1024];
	 
	     try{
	 
	    	//create output directory is not exists
	    	File folder = new File(OUTPUT_FOLDER);
	    	if(!folder.exists()){
	    		folder.mkdir();
	    	}
	 
	    	//get the zip file content
	    	ZipInputStream zis = 
	    		new ZipInputStream(new FileInputStream(zipFile));
	    	//get the zipped file list entry
	    	ZipEntry ze = zis.getNextEntry();
	 
	    	while(ze!=null){
	 
	    	   String fileName = ze.getName();
	           File newFile = new File(outputFolder + File.separator + fileName);
	 
	           System.out.println("file unzip : "+ newFile.getAbsoluteFile());
	 
	            //create all non exists folders
	            //else you will hit FileNotFoundException for compressed folder
	            new File(newFile.getParent()).mkdirs();
	 
	            FileOutputStream fos = new FileOutputStream(newFile);             
	 
	            int len;
	            while ((len = zis.read(buffer)) > 0) {
	       		fos.write(buffer, 0, len);
	            }
	 
	            fos.close();   
	            ze = zis.getNextEntry();
	    	}
	 
	        zis.closeEntry();
	    	zis.close();
	 
	    	System.out.println("Done");
	 
	    }catch(IOException ex){
	       ex.printStackTrace(); 
	    }
	   }    

}
