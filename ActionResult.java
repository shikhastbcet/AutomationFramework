package ExceptionHandler;


//Custom class to hold both result and status
public class ActionResult {
 private String message;
 private String status; // Can be "success" or "fail" or other indicators

 // Constructor
 public ActionResult(String message, String status) {
     this.message = message;
     this.status = status;
 } 

 // Getters
 public String getMessage() {
     return message;
 }

 public String getStatus() {
     return status;
 }

 // Setters 
 public void setMessage(String message) {
     this.message = message;
 }

 public void setStatus(String status) {
     this.status = status;
 }
}
