<html>
<body>
<h1>SAP JCO REST API</h1>
<h2>Available services</h2>
<ul>
<li><a href="http://localhost:8080/sapjcorestapi/rest/order/5">/rest/order/[nro]</a></li>
<li><a href="http://localhost:8080/sapjcorestapi/rest/user/DOEJAMES1">/rest/user/[username]</a></li>
<li><a href="http://localhost:8080/sapjcorestapi/rest/customer/1">/rest/customer</a> POST {"customerNro":"17","salesOrg":"0001","distributionChannel":"01","division":"01"}</li>
<li><a href="http://localhost:8080/sapjcorestapi/rest/customersearch">/rest/customersearch</a> POST {"customerNro":"186*","salesOrg":"0001","distributionChannel":"01","division":"01"}</li>

<!--<li><a href="http://localhost:8080/sapjcorestapi/rest/orders/123">/rest/orders/nro</a></li>-->
<!--<li><a href="http://localhost:8080/sapjcorestapi/rest/users/234/">/rest/users/customer</a></li>-->
<!--<li><a href="http://localhost:8080/sapjcorestapi/rest/atp/222/">/rest/atp/material</a></li>-->

<li><a href="http://localhost:8080/sapjcorestapi/rest/flight">/rest/flight</a> POST {"carrier":"LH","connectionNumber":"0400","date":"20151010"} </li>
<li><a href="http://localhost:8080/sapjcorestapi/rest/flightlist">/rest/flightlist</a> POST {"fromCountry":"DE","fromCity":"FRANKFURT","toCountry":"US","toCity":"NEW YORK","maxRead":"12"} </li>
</ul>
</body>
</html>
