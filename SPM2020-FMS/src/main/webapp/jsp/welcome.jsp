
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
                <div class="jumbotron ">
                  <h1 class="display-4">Welcome ${user.name} ${user.surname} !</h1>
                  <p class="lead">This is a simple hero unit, a simple jumbotron-style component for calling extra attention to featured content or information.</p>
                  <hr class="my-4">
                  <p>It uses utility classes for typography and spacing to space content out within the larger container.</p>
                  <p class="lead">
                    <a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a>
                  </p>
            </div>
         </div>
        </div>
     </div>
      <div class="container">
       <div class="row">
          <div class="col-sm-6">
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Choose a parking place</h5>
                <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                <a href="#" class="btn btn-primary">Go somewhere</a>
              </div>
            </div>
          </div>
          <div class="col-sm-6">
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Manage your reservations</h5>
                <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                <a href="#" class="btn btn-primary">Go somewhere</a>
              </div>
            </div>
          </div>
        </div>
     </div>
    </main>
  
</body>
</html>