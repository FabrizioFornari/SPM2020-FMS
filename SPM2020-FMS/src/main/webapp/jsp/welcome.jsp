
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<title>Welcome</title>

</head>
<body>

	<jsp:include page="navBar.jsp"></jsp:include>
	
    <main>
     <div class="container">
         <div class="row justify-content-center">
            <div class="col=12 mt-3">
                <div class="jumbotron" style="background-color: #826137ab;" >
                  <h1 class="display-4">Hello ${user.name} !</h1>
             
                  <hr class="my-4">
                
                  <p class="lead">
                    <button class="btn btn-outline-warning btn-lg"  >Find a place</button>
                      <button class="btn btn-outline-warning btn-lg"  >My Reservations</button>
                  </p>
                    <p class="lead">
                
                   <a href="myCars">   <button class="btn btn-outline-warning btn-lg" style="width:100%;" >My Cars</button></a>
                  </p>
                
            </div>
         </div>
        </div>
     </div>
 
    </main>
  
</body>
</html>