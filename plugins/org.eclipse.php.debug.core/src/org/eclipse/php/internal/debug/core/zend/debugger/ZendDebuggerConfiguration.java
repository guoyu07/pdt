/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.php.internal.debug.core.zend.debugger;

import java.io.File;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration;
import org.eclipse.php.internal.debug.core.launching.PHPExecutableLaunchDelegate;
import org.eclipse.php.internal.debug.core.launching.PHPWebPageLaunchDelegate;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.swt.widgets.Shell;

/**
 * Zend's debugger configuration class.
 * 
 * @author Shalom Gibly
 * @since PDT 1.0
 */
public class ZendDebuggerConfiguration extends AbstractDebuggerConfiguration {

	public static final String ID = "org.eclipse.php.debug.core.zendDebugger"; //$NON-NLS-1$

	private static final String EXTENSION_MODULE_ID = "Zend Debugger"; //$NON-NLS-1$

	/**
	 * Constructs a new ZendDebuggerConfiguration.
	 */
	public ZendDebuggerConfiguration() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration
	 * #getModuleId()
	 */
	@Override
	public String getModuleId() {
		return EXTENSION_MODULE_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration#
	 * openConfigurationDialog(org.eclipse.swt.widgets.Shell)
	 */
	public void openConfigurationDialog(Shell parentShell) {
		new ZendDebuggerConfigurationDialog(this, parentShell).open();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration
	 * #getPort()
	 */
	public int getPort() {
		return preferences.getInt(PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration
	 * #setPort(int)
	 */
	public void setPort(int port) {
		preferences.setValue(PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT, port);
	}

	/**
	 * @return port for broadcasting Studio settings to the ToolBar or Zend GUI
	 */
	public int getBroadcastPort() {
		return preferences
				.getInt(PHPDebugCorePreferenceNames.ZEND_DEBUG_BROADCAST_PORT);
	}

	/**
	 * @param broadcastPort
	 *            Port for broadcasting Studio settings to the ToolBar or Zend
	 *            GUI
	 */
	public void setBroadcastPort(int broadcastPort) {
		preferences.setValue(
				PHPDebugCorePreferenceNames.ZEND_DEBUG_BROADCAST_PORT,
				broadcastPort);
	}

	/**
	 * @return dummy PHP file name
	 */
	public String getDummyFile() {
		return preferences
				.getString(PHPDebugCorePreferenceNames.ZEND_DEBUG_DUMMY_FILE);
	}

	/**
	 * @param dummyFile
	 *            dummy PHP file name
	 */
	public void setDummyFile(String dummyFile) {
		preferences.setValue(PHPDebugCorePreferenceNames.ZEND_DEBUG_DUMMY_FILE,
				dummyFile);
	}

	public boolean isUseNewProtocol() {
		return preferences
				.getBoolean(PHPDebugCorePreferenceNames.ZEND_NEW_PROTOCOL);
	}

	public void setUNewProtocol(boolean enable) {
		preferences.setValue(PHPDebugCorePreferenceNames.ZEND_NEW_PROTOCOL,
				enable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration#
	 * getScriptLaunchDelegateClass()
	 */
	public String getScriptLaunchDelegateClass() {
		return PHPExecutableLaunchDelegate.class.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration#
	 * getWebLaunchDelegateClass()
	 */
	public String getWebLaunchDelegateClass() {
		return PHPWebPageLaunchDelegate.class.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration
	 * #applyDefaults()
	 */
	public void applyDefaults() {
		setPort(preferences
				.getDefaultInt(PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT));
		preferences.setValue(PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO,
				preferences.getDefaultBoolean(
						PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO));
		setBroadcastPort(preferences.getDefaultInt(
				PHPDebugCorePreferenceNames.ZEND_DEBUG_BROADCAST_PORT));
		setDummyFile(preferences.getDefaultString(
				PHPDebugCorePreferenceNames.ZEND_DEBUG_DUMMY_FILE));
		save();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration
	 * #validate()
	 */
	public IStatus validate(PHPexeItem item) {
		File executable = item.getExecutable();
		PHPexes.changePermissions(executable);
		if (isInstalled(item, EXTENSION_MODULE_ID))
			return Status.OK_STATUS;
		return new Status(
				IStatus.WARNING,
				PHPDebugPlugin.ID,
				PHPDebugCoreMessages.ZendDebuggerConfiguration_ZendDebuggerNotInstalledError);
	}

}
