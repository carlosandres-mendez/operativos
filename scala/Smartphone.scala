// A Scala program to illustrate  
// how to create a class  
    
// Name of the class is Smartphone  
class Smartphone  
{  
        
    // Class variables  
    var number: Int = 16
    var nameofcompany: String = "Apple"
        
    // Class method  
    def Display()  
    {  
        println("Name of the company : " + nameofcompany);  
        println("Total number of Smartphone generation: " + number);  
    }  
}  
object Main   
{  
        
    // Main method  
    def main(args: Array[String])   
    {  
            
        // Class object  
        var obj = new Smartphone();  
        obj.Display();  
    }  
}  