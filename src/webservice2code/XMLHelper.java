package webservice2code;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLHelper extends myClass {

    private Document doc;

    private ArrayList<Operation> methodsList;

    private ArrayList<Parameter> typeList;

    private String webserviceName;

    public Vector myOperation;
    public Vector myParameter;
    public Vector myClasss;

    public void parseWsdl(String url) throws IOException, ParserConfigurationException {
        try {
            URL myUrl = new URL(url);
            URLConnection conn = myUrl.openConnection();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder BB = factory.newDocumentBuilder();
            doc = (Document) BB.parse(conn.getInputStream());
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("wsdl:service");
            Node serviceNode = nodes.item(0);

            if (serviceNode.getNodeType() == Node.ELEMENT_NODE) {
                Element serviceElement = (Element) serviceNode;

                //serviceNode
                //  PropertyContainer.ServiceName = serviceElement.getAttribute("name");
                //Get Bindings
                NodeList bindings = doc.getElementsByTagName("wsdl:binding");

                //System.out.println("Total no of bindings : " + bindings.getLength());
                //get binding and then the Methods
                for (int s = 0; s < bindings.getLength(); s++) {
                    Node bindingNode = bindings.item(s);
                    if (bindingNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element bindingElement = (Element) bindingNode;

                        if (bindingElement.getAttribute("name").equals(myClass.GetSoapPortName())) {
                            String portTypeNS = bindingElement.getAttribute("type").replaceFirst("tns:", "");

                                    //System.out.println("Type : " + portTypeNS);
                            //get operations
                            NodeList operations = bindingElement.getElementsByTagName("wsdl:operation");

                                    //System.out.println("Total no of bindings : " + operations.getLength());
                                    //instatiate the Function array now we know how many functions there are.
                            //Loop through Functions
                            for (int op = 0; op < operations.getLength(); op++) {
                                Node operationNode = operations.item(op);

                                if (bindingNode.getNodeType() == Node.ELEMENT_NODE) {
                                    //get element
                                    Element operationElement = (Element) operationNode;

                                    //create new instance
                                    Operation newFunc = new Operation();
                                    newFunc.name = operationElement.getAttribute("name");

                                    //get operation list and element
                                    NodeList soapOpList = operationElement.getElementsByTagName("soap:operation");
                                    Element soapOpElement = (Element) soapOpList.item(0);

                                    newFunc.soapAction = soapOpElement.getAttribute("soapAction");

                                    //get port types
                                    NodeList portTypes = doc.getElementsByTagName("wsdl:portType");

                                    //loop through elements
                                    for (int pt = 0; pt < portTypes.getLength(); pt++) {
                                        Node portTypeNode = portTypes.item(pt);

                                        if (portTypeNode.getNodeType() == Node.ELEMENT_NODE) {
                                            Element portTypeElement = (Element) portTypeNode;

                                            //check to see if it is the same as the current operation
                                            if (portTypeElement.getAttribute("name").equals(portTypeNS)) {
                                                //get the operations for this portType
                                                NodeList operationList = portTypeElement.getElementsByTagName("wsdl:operation");

                                                //loop through operation list
                                                for (int ol = 0; ol < operationList.getLength(); ol++) {
                                                    Node operationListNode = operationList.item(ol);

                                                    if (operationListNode.getNodeType() == Node.ELEMENT_NODE) {
                                                        Element operationListElement = (Element) operationListNode;

                                                        //see if the element matches the function anem
                                                        if (operationListElement.getAttribute("name").equals(newFunc.name)) {
                                                            //is this function - get input parameter type
                                                            NodeList inputList = operationListElement.getElementsByTagName("wsdl:input");
                                                            String inputMessage = ((Element) inputList.item(0)).getAttribute("message").replaceFirst("tns:", "");

                                                            //is this function - get return type
                                                            NodeList outputList = operationListElement.getElementsByTagName("wsdl:output");
                                                            String outputMessage = ((Element) outputList.item(0)).getAttribute("message").replaceFirst("tns:", "");

//
                                                                    //now need to get the Types of the return and property value
                                                            //get port types
                                                            NodeList messages = doc.getElementsByTagName("wsdl:message");

                                                            for (int ml = 0; ml < messages.getLength(); ml++) {
                                                                Node messageNode = messages.item(ml);

                                                                if (messageNode.getNodeType() == Node.ELEMENT_NODE) {
                                                                    //convert to element
                                                                    Element messageElement = (Element) messageNode;

                                                                    //compare against inputMessage name
                                                                    if (messageElement.getAttribute("name").equals(inputMessage)) {

                                                                        newFunc.returnType = ((Element) messageElement.getElementsByTagName("wsdl:part").item(0)).getAttribute("element").replaceFirst("tns:", "");

                                                                                //System.out.println("In " + newFunc.InputType);
                                                                    } else //compare against inputMessage name
                                                                    if (messageElement.getAttribute("name").equals(outputMessage)) {
                                                                                    //get response type
                                                                        //get input param type
                                                                        newFunc.OutputType = ((Element) messageElement.getElementsByTagName("wsdl:part").item(0)).getAttribute("element").replaceFirst("tns:", "");

                                                                    }

                                                                }

                                                            }

                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    }

                                    //add newFunc to functions array
                                    myClass.methodsList.add(newFunc);

                                }
                            }
                        } else {
                        }

                    }

                }

            }

        } catch (SAXException ex) {
            Logger.getLogger(XMLHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ArrayList<Operation> getMethodsList() {
        return null;
    }

}
