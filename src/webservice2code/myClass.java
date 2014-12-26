package webservice2code;


import java.util.ArrayList;

public class myClass {

  public static String name;
  public static String ServiceName;

  public static ArrayList<Operation> methodsList;
  public static ArrayList<SoapClass> Classes = new ArrayList<SoapClass>();
  public static ArrayList<SoapClass> ComplexTypes = new ArrayList<SoapClass>();
    
  public void addMethod(Operation method) {
  }

  public String generateClassCode() {
  return null;
  }
  public static String GetSoapPortName()
        {
            return ServiceName + "Soap";
        }

        public static String GetSoap12PortName()
        {
            return ServiceName + "Soap12";
        }

        ///Get Class with name
        public static SoapClass GetClassWithName(String name)
        {
            //first try the Classes array
            for (SoapClass spC : Classes)
            {
                if (spC.Name.equals(name))
                {
                    return spC;
                }
            }

            //then try ComplexTypes
            for (SoapClass spC : ComplexTypes)
            {
                if (spC.Name.equals(name))
                {
                    return spC;
                }
            }
            return null;
        }

        public static void reset()
        {
           
            Classes = new ArrayList<SoapClass>();
            ComplexTypes = new ArrayList<SoapClass>();
        }
}

