package azkaban.triggertype.HdfsDataTrigger;

import org.mortbay.jetty.servlet.ServletHolder;

import org.mortbay.jetty.servlet.Context;
import azkaban.trigger.TriggerAgent;
import azkaban.utils.Props;
import azkaban.webapp.AzkabanWebServer;
import azkaban.webapp.servlet.AbstractAzkabanServlet;
import azkaban.webapp.servlet.TriggerPlugin;

public class HdfsDataTriggerPlugin implements TriggerPlugin {

	private final HdfsDataTriggerManager hdfsDataTriggerManager;
	private final HdfsDataTriggerServelet hdfsDataTriggerServlet;
	
	private int pluginOrder = 0; 
	private boolean pluginHidden = false;
	private String pluginName = "HdfsDataTrigger";
	
	
	public HdfsDataTriggerPlugin(String name, Props props, Context root, AzkabanWebServer azkabanWebApp) {
		
		HdfsDataTriggerManager hdfsDataTriggerManager = new HdfsDataTriggerManager(props, azkabanWebApp.getTriggerManager(), azkabanWebApp.getExecutorManager(), azkabanWebApp.getProjectManager());
		HdfsDataTriggerServelet hdfsDataTriggerServlet = new HdfsDataTriggerServelet(props, hdfsDataTriggerManager, azkabanWebApp.getProjectManager());
		this.hdfsDataTriggerManager = hdfsDataTriggerManager;
		this.hdfsDataTriggerServlet = hdfsDataTriggerServlet;
		
//		String pluginName = props.getString("viewer.name");
//		String pluginWebPath = props.getString("trigger.path");
		pluginOrder = props.getInt("viewer.order", 0);
		pluginHidden = props.getBoolean("viewer.hidden", false);
		root.addServlet(new ServletHolder(hdfsDataTriggerServlet), "/" + hdfsDataTriggerServlet.getWebPath() + "/*");
	}

	@Override
	public AbstractAzkabanServlet getServlet() {
		return hdfsDataTriggerServlet;
	}

	@Override
	public TriggerAgent getAgent() {
		return hdfsDataTriggerManager;
	}
	
	@Override
	public void load() {
		hdfsDataTriggerManager.load();
	}

	@Override
	public String getPluginName() {
		return pluginName;
	}

	@Override
	public String getPluginPath() {
		return hdfsDataTriggerServlet.getWebPath();
	}

	@Override
	public int getOrder() {
		return pluginOrder;
	}

	@Override
	public boolean isHidden() {
		return pluginHidden;
	}

	@Override
	public void setHidden(boolean hidden) {
		this.pluginHidden = hidden;
	}

	@Override
	public String getInputPanelVM() {
		return hdfsDataTriggerServlet.getInputPanelVM();
	}
	
	
}
