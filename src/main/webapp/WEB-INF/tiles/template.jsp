<%@ page pageEncoding="UTF-8" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<html>
    <head>
        <tiles:insertAttribute name="meta" />
    </head>
    <body>
        <header>
            <tiles:insertAttribute name="header" />
        </header>
        <div id="body">
            <tiles:insertAttribute name="body" />
        </div>
        <footer>
            <tiles:insertAttribute name="footer" />
        </footer>
    </body>
</html>