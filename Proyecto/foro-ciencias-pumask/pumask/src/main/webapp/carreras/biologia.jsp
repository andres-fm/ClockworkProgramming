<ui:composition
	template="/WEB-INF/facelets/templates/layout.xhtml"
    xmlns="http://www.w3.org/1999/xhtml"
    xmlns:h="http://xmlns.jcp.org/jsf/html"
    xmlns:ui="http://xmlns.jcp.org/jsf/facelets"
    xmlns:p="http://primefaces.org/ui"
    xmlns:f="http://java.sun.com/jsf/core"
	xmlns:c="http://xmlns.jcp.org/jsp/jstl/core">


  <ui:define name="content">

  <h:head>
    <title>Subforo Biología</title>
  </h:head>
  <h:body>

	<h3> Interfaz de foro </h3>

	<c:choose>
		<c:when test="#{controladorSesion.estaEnSesion()}">
				<p:commandButton value="Agregar Pregunta" onclick="PF('agp').show();" type="button" />
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>

        
	<p:dialog header="Agregar Pregunta" widgetVar="agp" dynamic="true" showEffect="explode" hideEffect="fold">


	<h:form id="agregarP">

	  	<h:outputText value="Pregunta"/>

	  	<p:inputText value="#{agregacionPregunta.pregunta.contenido}" required="true" id="pregunta" label="pregunta" />

	  	<h:outputText value="Información adicional"/>

	  	<p:inputTextarea value="#{agregacionPregunta.pregunta.detalle}" required="false" id="detalles" label="detalles"
	  	autoResize="true" maxlength="150"/>

		<p:selectOneMenu value = "#{agregacionPregunta.pregunta.categoria}">
			<f:selectItem itemLabel="Servicio Social" itemValue="servicio social"/>
			<f:selectItem itemLabel="Titulación" itemValue="titulacion"/>
		</p:selectOneMenu>

			<h:commandButton action="#{agregacionPregunta.agregaPregunta()}"
							 value="Agregar Pregunta"/>
		</h:form>


	</p:dialog>


	<p:tabView>
        <p:tab title="Titulación">
            <p:dataTable value="#{agregacionPregunta.obtenerPreguntasTitulacion()}" rows="5" paginator="true" var="pregunta">
				<f:facet name="header">
					<h:outputText value="#{agregacionPregunta.pregunta.carrera}" />
				</f:facet>
				<p:column headerText="Preguntas">
   					<h:outputText value="#{pregunta.contenido}" />
  				</p:column>
			</p:dataTable>
        </p:tab>
        <p:tab title="Servicio Social">
            <p:dataTable value="#{agregacionPregunta.obtenerPreguntasServicioSocial()}" rows="5" paginator="true" var="pregunta">
				<f:facet name="header">
					<h:outputText value="#{agregacionPregunta.pregunta.carrera}" />
				</f:facet>
				<p:column headerText="Preguntas" >
   					<h:outputText value="#{pregunta.contenido}"/>
  				</p:column>
			</p:dataTable>
        </p:tab>
    </p:tabView>


</h:body>

</ui:define>

</ui:composition>