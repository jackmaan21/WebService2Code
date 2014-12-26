/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservice2code;

/**
 *
 * @author jackhacker
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.List;


public class SoapClass
{
    public enum ClassType { Parameter, Response, ComplexType, Unknown};

    public String Name;
    public ClassType Type;
    public boolean isArray;
    public String ElementType;
    public String SuperClassType = "BaseObject";


    public List<SoapClassProperty> Properties;


    public SoapClass(String Name)
    {
        this.Name = Name;

        this.Properties = new ArrayList<SoapClassProperty>();
    }
}
