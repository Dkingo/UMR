package framework;

/**
 * 
 * ˵��:����������
 * 
 * @author LS
 * 
 * */
import java.awt.*;
import java.util.Enumeration;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class Tools {

	// ����Table��ʽ
	@SuppressWarnings("static-access")
	public static void setTableStyle(JTable jtb) {

		// ����ѡ�����
		jtb.setSelectionBackground(new Color(51, 153, 255));
		jtb.setBackground(new Color(230,230,230));
		// �����и�
		jtb.setRowHeight(40);
		jtb.setAutoCreateRowSorter(true);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) jtb.getTableHeader().getDefaultRenderer();
		renderer.setHorizontalAlignment(renderer.CENTER);

		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		jtb.setDefaultRenderer(Object.class, r);

		jtb.setFont(AllFont.Static);
		fitTableColumns(jtb);
	}

	// ��������Table����
	@SuppressWarnings("rawtypes")
	private static void fitTableColumns(JTable myTable) {
		myTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JTableHeader header = myTable.getTableHeader();
		int rowCount = myTable.getRowCount();
		Enumeration columns = myTable.getColumnModel().getColumns();
		while (columns.hasMoreElements()) {
			TableColumn column = (TableColumn) columns.nextElement();
			int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
			int width = (int) header.getDefaultRenderer()
					.getTableCellRendererComponent(myTable, column.getIdentifier(), false, false, -1, col)
					.getPreferredSize().getWidth();
			for (int row = 0; row < rowCount; row++) {
				int preferedWidth = (int) myTable.getCellRenderer(row, col)
						.getTableCellRendererComponent(myTable, myTable.getValueAt(row, col), false, false, row, col)
						.getPreferredSize().getWidth();
				width = Math.max(width, preferedWidth);
			}
			// �����п�����������Ӧ
			header.setResizingColumn(column);
			column.setWidth(width + myTable.getIntercellSpacing().width);
		}
	}

	// ����JScrollPane��ʽ
	public static void setJspStyle(JScrollPane jsp) {

		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsp.getViewport().setOpaque(false);
		jsp.getVerticalScrollBar().setOpaque(false);
	}

}
