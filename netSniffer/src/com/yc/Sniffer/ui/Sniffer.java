package com.yc.Sniffer.ui;

import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TableItem;

public class Sniffer {

	protected Shell shlSniffer;
	private Table table;
	private Text text;
	private Text text_1;
	private Text text_2;           
	private Button btnNewButton;     //开始按钮
	private Button btnNewButton_1;  //停止按钮
	private PortScanTask pst;    //任务对象

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Sniffer window = new Sniffer();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlSniffer.open();
		shlSniffer.layout();
		while (!shlSniffer.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlSniffer = new Shell();
		shlSniffer.setSize(677, 510);
		shlSniffer.setText("Sniffer");
		shlSniffer.setLayout(new FillLayout(SWT.HORIZONTAL));

		SashForm sashForm = new SashForm(shlSniffer, SWT.BORDER | SWT.SMOOTH
				| SWT.VERTICAL);

		Composite composite = new Composite(sashForm, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));

		Group group = new Group(composite, SWT.NONE);
		group.setText("\u67E5\u8BE2\u6761\u4EF6");

		Label lblNewLabel = new Label(group, SWT.NONE);
		lblNewLabel.setBounds(25, 23, 54, 12);
		lblNewLabel.setText("\u6307\u5B9AIP:");

		text = new Text(group, SWT.BORDER);
		text.setText("192.168.1.136");
		text.setBounds(98, 17, 198, 18);

		Label lblNewLabel_1 = new Label(group, SWT.NONE);
		lblNewLabel_1.setBounds(25, 58, 54, 12);
		lblNewLabel_1.setText("\u8D77\u59CB\u7AEF\u53E3:");

		Label lblNewLabel_2 = new Label(group, SWT.NONE);
		lblNewLabel_2.setBounds(354, 23, 54, 12);
		lblNewLabel_2.setText("\u7EC8\u6B62\u7AEF\u53E3:");

		text_1 = new Text(group, SWT.BORDER);
		text_1.setText("3320");
		text_1.setBounds(445, 17, 64, 18);

		text_2 = new Text(group, SWT.BORDER);
		text_2.setText("3300");
		text_2.setBounds(99, 58, 70, 18);

		btnNewButton = new Button(group, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				table.removeAll();
				String ip = text.getText().toString();
				int startPort = Integer.parseInt(text_2.getText().toString());
				int endPort = Integer.parseInt(text_1.getText().toString());
				 pst = new PortScanTask(ip, new NotifyInfo() {
					@Override
					public void notify(final MachineInfo machineInfo) {
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {
								TableItem tableItem = new TableItem(table,
										SWT.NONE);
								tableItem.setText(new String[] {
										machineInfo.getIp(),
										machineInfo.getPort() + "",
										machineInfo.getIsBusy(),
										machineInfo.getAppInfo() });

							}
						});
					}
				}, startPort, endPort, new NotifyFinished() {
					@Override
					public void notify(boolean flag) {
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {
								btnNewButton.setEnabled(true);
								btnNewButton_1.setEnabled(false);
							}
						});
					}
				});
				Thread t = new Thread(pst);
				t.start();
				btnNewButton.setEnabled(false);
				btnNewButton_1.setEnabled(true);
			}
		});
		btnNewButton.setBounds(237, 54, 72, 22);
		btnNewButton.setText("\u542F\u52A8");

		btnNewButton_1 = new Button(group, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				pst.setFlag(false);
				btnNewButton_1.setEnabled(false);
				btnNewButton.setEnabled(true);
			}
		});
		btnNewButton_1.setBounds(352, 54, 72, 22);
		btnNewButton_1.setText("\u6682\u505C");
		btnNewButton_1.setEnabled(false);

		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));

		table = new Table(composite_1, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("\u4E3B\u673AIP");

		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText("\u7AEF\u53E3");

		TableColumn tblclmnNewColumn_3 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_3.setWidth(100);
		tblclmnNewColumn_3.setText("\u662F\u5426\u5360\u7528");

		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(100);
		tblclmnNewColumn_2.setText("\u6709\u53EF\u80FD\u7684\u5E94\u7528");

		sashForm.setWeights(new int[] { 104, 361 });

	}
}
