<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link
	href="${pageContext.request.contextPath}/resources/css/statisticsStyle.css"
	rel="stylesheet">
<title>Statistics</title>
</head>
<body>
<jsp:include page="navBar.jsp"></jsp:include>

<div class='title'>
  <h1>
    [Parking statistics of ...]
  </h1>
  <h2 style="color:#ececec;">
    Here you can check all the statistics for you city ... (more details to be added)
  </h2>
</div>
<div class='ui'>
  <div class='ui_box'>
    <div class='ui_box__inner'>
      <h2>
        Conversion Rate
      </h2>
      <p>Lorem ipsum dolor sit amet</p>
      <div class='stat'>
        <span>58%</span>
      </div>
      <div class='progress'>
        <div class='progress_bar'></div>
      </div>
      <p>Lorem ipsum dolor sit amet. Some more super groovy information about this stat.</p>
    </div>
    <div class='drop'>
      <p>Take a closer look</p>
      <div class='arrow'></div>
    </div>
  </div>
  <div class='ui_box'>
    <div class='ui_box__inner'>
      <h2>
        Sales By Type
      </h2>
      <p>Lorem ipsum dolor sit amet</p>
      <div class='stat_left'>
        <ul>
          <li>
            Electical
          </li>
          <li>
            Clothing
          </li>
          <li>
            Entertainment
          </li>
          <li>
            Kitchen
          </li>
        </ul>
      </div>
      <div class='progress_graph'>
        <div class='progress_graph__bar--1'></div>
        <div class='progress_graph__bar--2'></div>
        <div class='progress_graph__bar--3'></div>
        <div class='progress_graph__bar--4'></div>
      </div>
      <p>Lorem ipsum dolor sit amet. Some more super groovy information.</p>
    </div>
    <div class='drop'>
      <p>Take a closer look</p>
      <div class='arrow'></div>
    </div>
  </div>
  <div class='ui_box'>
    <div class='ui_box__inner'>
      <h2>
        Total Sales
      </h2>
      <p>Lorem ipsum dolor sit amet</p>
      <div class='stat'>
        <span>$34,403.93</span>
      </div>
      <div class='progress'>
        <div class='progress_bar--two'></div>
      </div>
      <p>Lorem ipsum dolor sit amet. Some more super groovy information about this stat.</p>
    </div>
    <div class='drop'>
      <p>Take a closer look</p>
      <div class='arrow'></div>
    </div>
  </div>
</div>
</body>
</html>