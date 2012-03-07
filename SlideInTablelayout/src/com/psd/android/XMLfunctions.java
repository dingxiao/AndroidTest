/**
 * 
 */
package com.psd.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
//do not use android.dom
import org.w3c.dom.Element;

/**
 * @author ding xiao
 *
 */
public class XMLfunctions {
	public static String getXMLPost(String webUrl){
		String line = null;

		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(webUrl);//("http://p-xr.com/xml");

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			line = EntityUtils.toString(httpEntity);

		} catch (UnsupportedEncodingException e) {
			line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
		} catch (MalformedURLException e) {
			line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
		} catch (IOException e) {
			line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
		}

		return line;

	}
	public static String getXMLGet(String webUrl){
		String line = null;

		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet(webUrl); //"http://www.spartanjava.com"
		try{
			HttpResponse response = httpClient.execute(httpGet, localContext);
			
			 			
			HttpEntity httpEntity = response.getEntity();
			line = EntityUtils.toString(httpEntity);

		} catch (UnsupportedEncodingException e) {
			line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
		} catch (MalformedURLException e) {
			line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
		} catch (IOException e) {
			line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
		}

		return line;

	}
	public static Document XMLfromString(String xml){

		Document doc = null;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        try {

			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource is = new InputSource();
		        is.setCharacterStream(new StringReader(xml));
		        doc = db.parse(is); 

			} catch (ParserConfigurationException e) {
				System.out.println("XML parse error: " + e.getMessage());
				return null;
			} catch (SAXException e) {
				System.out.println("Wrong XML file structure: " + e.getMessage());
	            return null;
			} catch (IOException e) {
				System.out.println("I/O exeption: " + e.getMessage());
				return null;
			}

	        return doc;

		}	
	public static int numResults11(Document doc){

		if ( doc == null)
			 return 0;

		return 1;

	}	
    public static int numResults(Document doc){     
        Node results = doc.getDocumentElement();
        int res = -1;

        try{
            res = Integer.valueOf(results.getAttributes().getNamedItem("count").getNodeValue());
        }catch(Exception e ){
            res = -1;
        }

        return res;
    }

    public static String getValue(Element item, String str) {       
        NodeList n = item.getElementsByTagName(str);        
        return XMLfunctions.getElementValue(n.item(0));
    }
    /** Returns element value
     * @param elem element (it is XML tag)
     * @return Element value otherwise empty String
     */
    public final static String getElementValue( Node elem ) {
        Node kid;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( kid = elem.getFirstChild(); kid != null; kid = kid.getNextSibling() ){
                    if( kid.getNodeType() == Node.TEXT_NODE  ){
                        return kid.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
	
	
    private static List<Element> parseDocument(Document dom, String rootName, String elemName){
        //get the root elememt
        //Element docEle = dom.getDocumentElement();
        NodeList docEle = dom.getElementsByTagName(rootName);//("ProductFamilies");
        Element docEle2 = null;
        if (null != docEle) docEle2 = (Element)docEle.item(0);

        //get a nodelist of <employee> elements
        NodeList nl = docEle2.getElementsByTagName(elemName);//("ProductFamily");
        //NodeList nl = null;//docEle2.getChild(localName)("ProductFamily");
        if(nl != null && nl.getLength() > 0) {
                List<Element> myEmpls = new LinkedList<Element>();
                for(int i = 0 ; i < nl.getLength();i++) {

                        //get the employee element
                        Element el = (Element)nl.item(i);

                        //get the Employee object
                        //Employee e = getEmployee(el);

                        //add it to list
                        myEmpls.add(el);
                }
                return myEmpls;
        }
        return null;
}
/**
 * take a xml element and the tag name, look for the tag and get
 * the text content
 * i.e for <employee><name>John</name></employee> xml snippet if
 * the Element points to employee node and tagName is name I will return John
 * @param ele
 * @param tagName
 * @return
 */
private static String getTextValue(Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if(nl != null && nl.getLength() > 0) {
                Element el = (Element)nl.item(0);
                if (el != null && el.getFirstChild() != null)
                textVal = el.getFirstChild().getNodeValue();
        }

        return textVal;
}
/**
 * take an xml element contains list of string elements and the tag name, look for the tag and get
 * the list of text content
 * i.e for <employee><name>John</name></employee> xml snippet if
 * the Element points to employee node and tagName is name I will return John
 * @param ele
 * @param tagName--tag name
 * @param subName--element tag name
 * @return list of strings
 */
private static List<String> getListValue(Element ele, String tagName, String subName) {

        NodeList nl = ele.getElementsByTagName(tagName);
        if(nl != null && nl.getLength() > 0) {
                //NodeList nl2 = nl.item(0).getChildNodes();
                Node nl22 = (nl.item(0)).getFirstChild();
                NodeList nl2 = ((Element)(nl.item(0))).getElementsByTagName(subName);
                List<String> ll = new LinkedList<String>();
                for (int i=0; i < nl2.getLength(); i++){
                        Element el = (Element)nl2.item(i);
                        String va = el.getFirstChild().getNodeValue();
                        ll.add(va);
                }
                return ll;
        }

        return null;
}
/**
 * take an xml element contains list of sub elements and the tag name, look for the tag and get
 * the list of map contains tagName with its text content
 * @param ele
 * @param tagName--tag name
 * @param subName--element tag name
 * @return list of map
 */
private static List<HashMap<String,String>> getListMapTag(Element ele, String tagName, String subName, List<String> tagElements) {

        NodeList nl = ele.getElementsByTagName(tagName);
        if(nl != null && nl.getLength() > 0) {
                //NodeList nl2 = nl.item(0).getChildNodes();
                Node nl22 = (nl.item(0)).getFirstChild();
                NodeList nl2 = ((Element)(nl.item(0))).getElementsByTagName(subName);
                List<HashMap<String, String>> ll = new LinkedList<HashMap<String, String>>();
                for (int i=0; i < nl2.getLength(); i++){
                        Element el = (Element)nl2.item(i);
                        HashMap<String,String> m = new HashMap<String,String>();
                        for (String s: tagElements){
                            String tagElemName = s;
                            String tagElemVal = getTextValue(el,tagElemName);
                            m.put(tagElemName, tagElemVal);
                        }
                        ll.add(m);
                }
                return ll;
        }

        return null;
}


/**
 * Calls getTextValue and returns a int value
 * @param ele
 * @param tagName
 * @return
 */
private static Integer getIntValue(Element ele, String tagName) {
        //in production application you would catch the exception
    if (getTextValue(ele,tagName) == null)
        return null;
    return Integer.parseInt(getTextValue(ele,tagName));
}

	
	
}
