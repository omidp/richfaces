<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="Paint2DPropertySubviewID">
	<h:commandButton value="add test" action="#{paintData.addHtmlPaint2D}"></h:commandButton>
	<h:panelGrid columns="2" cellpadding="5px" border="2">
		<h:outputText value="Text"></h:outputText>
		<h:inputText value="#{paintData.text}">
			<a4j:support event="onchange" reRender="paint2dID" />
		</h:inputText>

		<h:outputText value="Width: "></h:outputText>
		<h:inputText value="#{paint2D.width}">
			<a4j:support event="onchange" reRender="paint2dID"></a4j:support>
		</h:inputText>

		<h:outputText value="Height: "></h:outputText>
		<h:inputText value="#{paint2D.height}">
			<a4j:support event="onchange" reRender="paint2dID"></a4j:support>
		</h:inputText>

		<h:outputText value="Vertical space: "></h:outputText>
		<h:inputText value="#{paint2D.vspace}">
			<a4j:support event="onchange" reRender="paint2dID"></a4j:support>
		</h:inputText>

		<h:outputText value="Horizontal space: "></h:outputText>
		<h:inputText value="#{paint2D.hspace}">
			<a4j:support event="onchange" reRender="paint2dID"></a4j:support>
		</h:inputText>

		<h:outputText value="cacheable:"></h:outputText>
		<h:selectBooleanCheckbox value="#{paint2D.cacheable}"
			onchange="submit();" />

		<h:outputText value="Align:"></h:outputText>
		<h:selectOneMenu value="#{paint2D.align}">
			<f:selectItem itemLabel="left" itemValue="left" />
			<f:selectItem itemLabel="middle" itemValue="middle" />
			<f:selectItem itemLabel="right" itemValue="right" />
			<f:selectItem itemLabel="bottom" itemValue="bottom" />
			<f:selectItem itemLabel="top" itemValue="top" />
			<a4j:support event="onclick" reRender="paint2dID"></a4j:support>
		</h:selectOneMenu>

		<h:outputText value="Format:"></h:outputText>
		<h:selectOneMenu value="#{paint2D.format}">
			<f:selectItem itemLabel="jpeg" itemValue="jpeg" />
			<f:selectItem itemLabel="gif" itemValue="gif" />
			<f:selectItem itemLabel="png" itemValue="png" />
			<a4j:support event="onclick" reRender="paint2dID"></a4j:support>
		</h:selectOneMenu>

		<h:outputText value="Background Colour"></h:outputText>
		<h:selectOneMenu value="#{paint2D.bgcolor}">
			<f:selectItem itemLabel="aqua" itemValue="aqua" />
			<f:selectItem itemLabel="blue" itemValue="blue" />
			<f:selectItem itemLabel="fuchsia" itemValue="fuchsia" />
			<f:selectItem itemLabel="gray" itemValue="gray" />
			<f:selectItem itemLabel="lime" itemValue="lime" />
			<f:selectItem itemLabel="maroon" itemValue="maroon" />
			<f:selectItem itemLabel="purple" itemValue="purple" />
			<f:selectItem itemLabel="red" itemValue="red" />
			<f:selectItem itemLabel="silver" itemValue="silver" />
			<f:selectItem itemLabel="teal" itemValue="teal" />
			<f:selectItem itemLabel="yellow" itemValue="yellow" />
			<f:selectItem itemLabel="white" itemValue="white" />
			<a4j:support event="onclick" reRender="paint2dID"></a4j:support>
		</h:selectOneMenu>

		<h:outputText value="Style" />
		<h:selectBooleanCheckbox value="#{paint2D.style}">
			<a4j:support event="onclick" reRender="paint2dID,borderID" />
		</h:selectBooleanCheckbox>

		<h:outputText value="Border: "></h:outputText>
		<h:inputText id="borderID" disabled="#{paint2D.style}"
			value="#{paint2D.border}">
			<a4j:support event="onchange" reRender="paint2dID"></a4j:support>
		</h:inputText>

		<h:outputText value="Rendered"></h:outputText>
		<h:selectBooleanCheckbox value="#{paint2D.rendered}"
			onchange="submit();">
		</h:selectBooleanCheckbox>
	</h:panelGrid>
	<br />
	<br />
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">
		<rich:column>
			<a4j:commandLink value="getData" reRender="findID"></a4j:commandLink>
		</rich:column>
		<rich:column id="findID">
			<h:outputText value="#{rich:findComponent('paint2dID').data.text}" />
		</rich:column>
	</h:panelGrid>
</f:subview>