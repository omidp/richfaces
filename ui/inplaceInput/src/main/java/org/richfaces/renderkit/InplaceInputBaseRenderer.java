/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */
package org.richfaces.renderkit;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.ajax4jsf.javascript.JSFunctionDefinition;
import org.ajax4jsf.javascript.ScriptUtils;
import org.ajax4jsf.renderkit.HeaderResourcesRendererBase;
import org.ajax4jsf.renderkit.RendererUtils;
import org.ajax4jsf.util.InputUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.richfaces.component.UIInplaceInput;

/**
 * @author Vladimir Molotkov
 * @since 3.2.0
 * InplaceInput Base renderer implementation
 *  	
 */
public class InplaceInputBaseRenderer extends HeaderResourcesRendererBase {
	
    private static Log logger = LogFactory.getLog(InplaceInputBaseRenderer.class);
    
    private static final String CONTROLS_FACET = "controls";
     
    private static final String INPLACE_COMPONENT = "COMPONENT"; 
    
    private static final String INPLACE_CHANGED = "CHANGED"; 

    private static final String INPLACE_VIEW = "VIEW"; 

    private static final String INPLACE_EDITABLE = "EDITABLE"; 
    
    private static final String INPLACE_NORMAL = "NORMAL";
    
    private static final String INPLACE_HOVERED = "HOVERED";
    
    private static final String INPLACE_CSS_PUBLIC = "rich-inplace";
    
    private static final String INPLACE_CSS_VIEW = "view";

    private static final String INPLACE_CSS_EDITABLE = "edit";
    
    private static final String INPLACE_CSS_CHANGE = "changed";
    
    private static final String INPLACE_CSS_HOVER = "hover";
    
    private static final String EMPTY_DEFAULT_LABEL = "\u00a0\u00a0\u00a0";
    
    private static enum States {
	NORMAL {String createCss(UIComponent component, String suffix){
	    StringBuilder builder = new StringBuilder();
	    builder.append(INPLACE_CSS_PUBLIC);
	    builder.append(" ");
	    builder.append(INPLACE_CSS_PUBLIC + "-" + suffix);
	    Object value = component.getAttributes().get(suffix + "Class");
	    if (value != null) {
		builder.append(" ");
		builder.append(value);
	    }
	    return builder.toString();
	};},
	HOVERED {String createCss(UIComponent component , String suffix){
	    StringBuilder builder = new StringBuilder();
	    builder.append(INPLACE_CSS_PUBLIC + "-" + "input-" + suffix + "-" + INPLACE_CSS_HOVER);
	    Object value = component.getAttributes().get(suffix + "HoverClass");
	    if (value != null) {
		builder.append(" ");
		builder.append(value);
	    }
	    return builder.toString();
	}},
	EDIT {String createCss(UIComponent component, String suffix){
	    StringBuilder builder = new StringBuilder();
	    builder.append(INPLACE_CSS_PUBLIC);
	    builder.append(" ");
	    builder.append(INPLACE_CSS_PUBLIC + "-" + suffix);
	    Object value = component.getAttributes().get(suffix + "Class");
	    if (value != null) {
		builder.append(" ");
		builder.append(value);
	    }
	    return builder.toString();
	}};
	abstract String createCss(UIComponent component, String suffix);		
    };
    

    	
    protected Class<? extends UIComponent>  getComponentClass() {
	return UIInplaceInput.class;
    }
    
    protected void doDecode(FacesContext context, UIComponent component) {
	UIInplaceInput inplaceInput = null;

	if (component instanceof UIInplaceInput) {
	    inplaceInput = (UIInplaceInput) component;
	} else {
	    if (logger.isDebugEnabled()) {
		logger.debug("No decoding necessary since the component " 
			     + component.getId() + 
			     " is not an instance or a sub class of UIInplaceInput");
	    }
	    return;
	}	
	
	String clientId = component.getClientId(context);
	if (clientId == null) {
	    throw new NullPointerException("component client id is NULL" );
	}

	if (InputUtils.isDisabled(inplaceInput) || InputUtils.isReadOnly(inplaceInput)) {
	    if (logger.isDebugEnabled()) {
		logger.debug(("No decoding necessary since the component "
			      + component.getId() + " is disabled"));
		}
	    return;
	}
	
	//TODO: 
	//Should use clientId instead?
	Map <String, String> request = context.getExternalContext().getRequestParameterMap();

	String newValue = (String)request.get(clientId);
	if (newValue != null) {
		inplaceInput.setSubmittedValue(newValue);
	}	
    }
    
    //TODO:
    //Move that code to template, maybe?
    public void encodeControlsFacet(FacesContext context, UIComponent component) throws IOException {
	UIComponent facet = component.getFacet(CONTROLS_FACET);
	if ((facet != null) && (facet.isRendered())) {
	    renderChild(context, facet);
	}
    }
    
    public boolean isControlsFacetExists(FacesContext context, UIComponent component) {
	UIComponent facet = component.getFacet(CONTROLS_FACET);
	if (facet != null && facet.isRendered()) {
	    return true;
	}
	return false;
    }

    public String encodeScriptAttributes(FacesContext context, UIComponent component) {
    	StringBuilder attributes = new StringBuilder();
    	attributes.append("var attributes = ");
    	
    	ScriptOptions options = new ScriptOptions(component);
    	
    	String defaultLabel = (String)component.getAttributes().get("defaultLabel");

    	if (defaultLabel == null || defaultLabel.trim().equals("")) {
	    defaultLabel = EMPTY_DEFAULT_LABEL;
    	}
    	
    	options.addOption("defaultLabel", defaultLabel);
    	options.addOption("showControls");
    	options.addOption("editEvent");
    	options.addOption("selectOnEdit");
    	options.addOption("verticalPosition", component.getAttributes().get("controlsVerticalPosition"));
    	options.addOption("horizontalPosition", component.getAttributes().get("controlsHorizontalPosition"));
    	options.addOption("inputWidth");
    	options.addOption("minInputWidth");
    	options.addOption("maxInputWidth");
    	attributes.append(options.toScript());
    	  	
    	return attributes.toString();
    }
      
    public String encodeScriptEvents(FacesContext context, UIComponent component) {
    	StringBuilder events = new StringBuilder();
    	
    	events.append("var events = ");
    	ScriptOptions options = new ScriptOptions(component);
    	options.addEventHandler("oneditactivation");
    	options.addEventHandler("onviewactivation");
    	options.addEventHandler("oneditactivated");
    	options.addEventHandler("onviewactivated");
    	events.append(options.toScript());
    	
    	return events.toString();
    }
        
    public String encodeInplaceInputCss(FacesContext context, UIComponent component) {
	StringBuilder cssMap = new StringBuilder();
    	cssMap.append("var classes = ");
    	
    	ScriptOptions mainMap = new ScriptOptions(component);
    	ScriptOptions componentClasses = new ScriptOptions(component);
    	ScriptOptions changedClasses = new ScriptOptions(component);
    	ScriptOptions viewClasses = new ScriptOptions(component);
   	
    	changedClasses.addOption(INPLACE_NORMAL, createCss(component, States.NORMAL, INPLACE_CSS_CHANGE) );
    	changedClasses.addOption(INPLACE_HOVERED, createCss(component,States.HOVERED, INPLACE_CSS_CHANGE));
    	
    	viewClasses.addOption(INPLACE_NORMAL, createCss(component, States.NORMAL, INPLACE_CSS_VIEW) );;
    	viewClasses.addOption(INPLACE_HOVERED, createCss(component,States.HOVERED, INPLACE_CSS_VIEW));
  	    	
    	componentClasses.addOption(INPLACE_CHANGED,changedClasses);
    	componentClasses.addOption(INPLACE_VIEW, viewClasses);
    	componentClasses.addOption(INPLACE_EDITABLE,createCss(component, States.EDIT, INPLACE_CSS_EDITABLE));
    	
    	mainMap.addOption(INPLACE_COMPONENT, componentClasses);
    	cssMap.append(mainMap.toString());
    	return cssMap.toString(); 
	}
    
    private String createCss(UIComponent component, States state, String suffix) {
	return state.createCss(component, suffix);
    }
    
    public String getAsEventHandler(FacesContext context, UIComponent component, String attributeName) {
	JSFunctionDefinition script = getUtils().getAsEventHandler(context, component, attributeName, null);  
	return ScriptUtils.toScript(script);
    }
   
    @Override
    public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue) throws ConverterException {
	return InputUtils.getConvertedValue(context, component, submittedValue); 
    }
    
    protected String getConvertedStringValue(FacesContext context, UIInplaceInput component, Object value) {
	return InputUtils.getConvertedStringValue(context, component, value);
    }
    
    protected boolean isEmptyDefaultLabel(String defaultLabel) {
    	if (EMPTY_DEFAULT_LABEL.equals(defaultLabel)) {
    		return true;
    	}
    	return false;
    }
}
