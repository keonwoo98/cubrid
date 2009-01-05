/*
 * Copyright (C) 2008 Search Solution Corporation. All rights reserved by Search Solution. 
 *
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met: 
 *
 * - Redistributions of source code must retain the above copyright notice, 
 *   this list of conditions and the following disclaimer. 
 *
 * - Redistributions in binary form must reproduce the above copyright notice, 
 *   this list of conditions and the following disclaimer in the documentation 
 *   and/or other materials provided with the distribution. 
 *
 * - Neither the name of the <ORGANIZATION> nor the names of its contributors 
 *   may be used to endorse or promote products derived from this software without 
 *   specific prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY 
 * OF SUCH DAMAGE. 
 *
 */

package cubridmanager.dialog;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;

import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

import cubridmanager.CommonTool;
import cubridmanager.Messages;
import cubridmanager.TextFocusAdapter;

public class ChangePasswordDialog extends Dialog {

	private Shell sShell = null;
	private Label lblUserName = null;
	private Text txtUserName = null;
	private Label lblNewPasswd = null;
	private Text txtNewPasswd = null;
	private Label lblConfirmPasswd = null;
	private Text txtConfirmPasswd = null;
	private Composite cmpBtnArea = null;
	private Button btnConfirm = null;
	private Button btnCancel = null;
	private String ret = "";
	private final String userName;

	public ChangePasswordDialog(Shell parent, String userName) {
		super(parent);
		this.userName = userName;
	}

	public ChangePasswordDialog(Shell parent, int style,
			String userName) {
		super(parent, style);
		this.userName = new String(userName);
	}

	public String doModal() {
		createSShell();
		CommonTool.centerShell(sShell);
		sShell.open();

		Display display = sShell.getDisplay();
		while (!sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		return ret;
	}

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		sShell = new Shell(getParent(), SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		sShell.setText(Messages.getString("TITLE.PASSWORDCHANGE"));
		sShell.setLayout(gridLayout);

		GridData gd_lblUserName = new GridData();
		gd_lblUserName.widthHint = 150;
		lblUserName = new Label(sShell, SWT.NONE);
		lblUserName.setText(Messages.getString("LABEL.PREVPASSWORD"));
		lblUserName.setLayoutData(gd_lblUserName);

		GridData gd_txtUserName = new GridData();
		gd_txtUserName.widthHint = 120;
		txtUserName = new Text(sShell, SWT.BORDER);
		txtUserName.setText(this.userName);
		txtUserName.setEditable(false);
		txtUserName.setLayoutData(gd_txtUserName);

		GridData gridData5 = new GridData();
		gridData5.widthHint = 150;
		lblNewPasswd = new Label(sShell, SWT.NONE);
		lblNewPasswd.setText(Messages.getString("LABEL.NEWPASSWORD"));
		lblNewPasswd.setLayoutData(gridData5);

		GridData gridData3 = new GridData();
		gridData3.widthHint = 120;
		txtNewPasswd = new Text(sShell, SWT.BORDER | SWT.PASSWORD);
		txtNewPasswd.setLayoutData(gridData3);
		txtNewPasswd.addFocusListener(new TextFocusAdapter());
		txtNewPasswd.setFocus();
		txtNewPasswd.addTraverseListener(new TraverseListener(){
			public void keyTraversed(TraverseEvent e) {
				if(e.character==SWT.CR)
				{
					e.doit = true;
					txtConfirmPasswd.setFocus();
				}
			}});
		txtNewPasswd.addVerifyListener(new VerifyListener(){
			public void verifyText(VerifyEvent e) {
				String str = txtNewPasswd.getText();
				if(str.length()>11&&e.keyCode!=SWT.DEL &&e.keyCode!='\b')
					e.doit=false;
			}});

		GridData gridData6 = new GridData();
		gridData6.widthHint = 150;
		lblConfirmPasswd = new Label(sShell, SWT.NONE);
		lblConfirmPasswd.setText(Messages.getString("LABEL.CONFIRMPASSWORD"));
		lblConfirmPasswd.setLayoutData(gridData6);

		GridData gridData4 = new GridData();
		gridData4.widthHint = 120;
		txtConfirmPasswd = new Text(sShell, SWT.BORDER | SWT.PASSWORD);
		txtConfirmPasswd.setLayoutData(gridData4);
		txtConfirmPasswd.addFocusListener(new TextFocusAdapter());
		txtConfirmPasswd.addTraverseListener(new TraverseListener(){
			public void keyTraversed(TraverseEvent e) {
				if(e.character==SWT.CR)
				{
					e.doit = true;
					btnConfirm.setFocus();
				}
			}});
		txtConfirmPasswd.addVerifyListener(new VerifyListener(){
			public void verifyText(VerifyEvent e) {
				String str = txtConfirmPasswd.getText();
				if(str.length()>11&&e.keyCode!=SWT.DEL &&e.keyCode!='\b')
					e.doit=false;
			}});
		createCmpBtnArea();

		sShell.pack();
	}

	/**
	 * This method initializes cmpBtnArea
	 * 
	 */
	private void createCmpBtnArea() {
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 2;
		gridLayout1.makeColumnsEqualWidth = true;
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = org.eclipse.swt.layout.GridData.CENTER;
		cmpBtnArea = new Composite(sShell, SWT.NONE);
		cmpBtnArea.setLayoutData(gridData);
		cmpBtnArea.setLayout(gridLayout1);

		GridData gridData11 = new GridData();
		gridData11.widthHint = 75;
		btnConfirm = new Button(cmpBtnArea, SWT.NONE);
		btnConfirm.setLayoutData(gridData11);
		btnConfirm.setText(Messages.getString("BUTTON.OK"));

		btnConfirm
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						
						if (CommonTool.ValidateCheckInIdentifier(
								txtNewPasswd.getText()).length() > 0) {
							CommonTool.WarnBox(sShell, Messages
									.getString("WARNING.NEWPASSWD"));
							txtNewPasswd.setFocus();
							return;
						}
						
						if (txtNewPasswd.getText().toLowerCase()
								.equals("admin")) {
							CommonTool.WarnBox(sShell, Messages
									.getString("MENU.PASSWORDADMIN"));
							txtNewPasswd.setFocus();
							return;
						}
						
						if (!txtNewPasswd.getText().equals(
								txtConfirmPasswd.getText())) {
							CommonTool.WarnBox(sShell, Messages
									.getString("WARNING.CONFIRMPASSWD"));
							txtNewPasswd.setFocus();
							return;
						}

						ret = txtNewPasswd.getText();
						sShell.dispose();
					}
				});
		GridData gridData21 = new GridData();
		gridData21.widthHint = 75;
		btnCancel = new Button(cmpBtnArea, SWT.NONE);
		btnCancel.setLayoutData(gridData21);
		btnCancel.setText(Messages.getString("BUTTON.CANCEL"));
		btnCancel
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						ret = "admin";
						sShell.dispose();
					}
				});
	}
}
