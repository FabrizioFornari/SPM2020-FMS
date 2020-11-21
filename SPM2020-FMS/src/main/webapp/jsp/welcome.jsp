<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
<title>Welcome</title>
<style type="text/css">    @import url("http://fonts.googleapis.com/css?family=Lato:100,300,400,700,900,400italic");
  
    body {
		padding: 60px 0px;
		background-color: rgb(220, 220, 220);
	}
    
    .event-list {
		list-style: none;
		font-family: 'Lato', sans-serif;
		margin: 0px;
		padding: 0px;
	}
	.event-list > li {
		background-color: rgb(255, 255, 255);
		box-shadow: 0px 0px 5px rgb(51, 51, 51);
		box-shadow: 0px 0px 5px rgba(51, 51, 51, 0.7);
		padding: 0px;
		margin: 0px 0px 20px;
	}
	.event-list > li > time {
		display: inline-block;
		width: 100%;
		color: rgb(255, 255, 255);
		background-color: rgb(197, 44, 102);
		padding: 5px;
		text-align: center;
		text-transform: uppercase;
	}
	.event-list > li:nth-child(even) > time {
		background-color: rgb(165, 82, 167);
	}
	.event-list > li > time > span {
		display: none;
	}
	.event-list > li > time > .day {
		display: block;
		font-size: 56pt;
		font-weight: 100;
		line-height: 1;
	}
	.event-list > li time > .month {
		display: block;
		font-size: 24pt;
		font-weight: 900;
		line-height: 1;
	}
	.event-list > li > img {
		width: 100%;
	}
	.event-list > li > .info {
		padding-top: 5px;
		text-align: center;
	}
	.event-list > li > .info > .title {
		font-size: 17pt;
		font-weight: 700;
		margin: 0px;
	}
	.event-list > li > .info > .desc {
		font-size: 13pt;
		font-weight: 300;
		margin: 0px;
	}
	.event-list > li > .info > ul,
	.event-list > li > .social > ul {
		display: table;
		list-style: none;
		margin: 10px 0px 0px;
		padding: 0px;
		width: 100%;
		text-align: center;
	}
	.event-list > li > .social > ul {
		margin: 0px;
	}
	.event-list > li > .info > ul > li,
	.event-list > li > .social > ul > li {
		display: table-cell;
		cursor: pointer;
		color: rgb(30, 30, 30);
		font-size: 11pt;
		font-weight: 300;
        padding: 3px 0px;
	}
    .event-list > li > .info > ul > li > a {
		display: block;
		width: 100%;
		color: rgb(30, 30, 30);
		text-decoration: none;
	} 
    .event-list > li > .social > ul > li {    
        padding: 0px;
    }
    .event-list > li > .social > ul > li > a {
        padding: 3px 0px;
	} 
	.event-list > li > .info > ul > li:hover,
	.event-list > li > .social > ul > li:hover {
		color: rgb(30, 30, 30);
		background-color: rgb(200, 200, 200);
	}
	.facebook a,
	.twitter a,
	.google-plus a {
		display: block;
		width: 100%;
		color: rgb(75, 110, 168) !important;
	}
	.twitter a {
		color: rgb(79, 213, 248) !important;
	}
	.google-plus a {
		color: rgb(221, 75, 57) !important;
	}
	.facebook:hover a {
		color: rgb(255, 255, 255) !important;
		background-color: rgb(75, 110, 168) !important;
	}
	.twitter:hover a {
		color: rgb(255, 255, 255) !important;
		background-color: rgb(79, 213, 248) !important;
	}
	.google-plus:hover a {
		color: rgb(255, 255, 255) !important;
		background-color: rgb(221, 75, 57) !important;
	}

	@media (min-width: 768px) {
		.event-list > li {
			position: relative;
			display: block;
			width: 100%;
			height: 120px;
			padding: 0px;
		}
		.event-list > li > time,
		.event-list > li > img  {
			display: inline-block;
		}
		.event-list > li > time,
		.event-list > li > img {
			width: 120px;
			float: left;
		}
		.event-list > li > .info {
			background-color: rgb(245, 245, 245);
			overflow: hidden;
		}
		.event-list > li > time,
		.event-list > li > img {
			width: 120px;
			height: 120px;
			padding: 0px;
			margin: 0px;
		}
		.event-list > li > .info {
			position: relative;
			height: 120px;
			text-align: left;
			padding-right: 40px;
		}	
		.event-list > li > .info > .title, 
		.event-list > li > .info > .desc {
			padding: 0px 10px;
		}
		.event-list > li > .info > ul {
			position: absolute;
			left: 0px;
			bottom: 0px;
		}
		.event-list > li > .social {
			position: absolute;
			top: 0px;
			right: 0px;
			display: block;
			width: 40px;
		}
        .event-list > li > .social > ul {
            border-left: 1px solid rgb(230, 230, 230);
        }
		.event-list > li > .social > ul > li {			
			display: block;
            padding: 0px;
		}
		.event-list > li > .social > ul > li > a {
			display: block;
			width: 40px;
			padding: 10px 0px 9px;
		}
	}</style>
</head>
<body>
<jsp:include page="navBar.jsp"></jsp:include>
<h2 align="center">Welcome ${name}!</h2><br>
    <div class="container">
		<div class="row">
			<div class="[ col-xs-12 col-sm-offset-2 col-sm-8 ]">
				<ul class="event-list">
					<li>
						<time datetime="2014-07-20">
							<span class="day">12</span>
							<span class="month">Nov</span>
							<span class="year">2020</span>
							<span class="time">15:00 - 17:00</span>
						</time>
						<img alt="Independence Day" src="https://farm4.staticflickr.com/3100/2693171833_3545fb852c_q.jpg" />
						<div class="info">
							<h2 class="title">Driver</h2>
							<p class="desc">Driver information</p>
						</div>
						<div class="social">
							<ul>
								<li class="facebook" style="width:33%;"><a href="#"><span class="fas fa-plus"></span></a></li>
								<li class="twitter" style="width:34%;"><a href="addCar"><span class="fas fa-car"></span></a></li>
								<li class="google-plus" style="width:33%;"><a href="#"><span class="fas fa-list"></span></a></li>
							</ul>
						</div>
					</li>
					
				

		

				

				
				</ul>
				
			
			</div>
		</div>
	</div>

</body>
</html>