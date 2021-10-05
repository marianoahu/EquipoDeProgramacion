package vista;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import modelo.Miembro;

import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.beans.PropertyChangeEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.DAOMiembro;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Vista extends JFrame {

	private JPanel contentPane;
	private Miembro miembro;
	private JTextField fieldNombre;
	private JTextField fieldEdad;
	private JTable table_1;
	private JComboBox comboCargo;
	private JComboBox comboBuscarCargo;
	private JButton btnBorrarMiembro;
	private JButton btnActualizarMiembro;
	private JButton btnAgregarMiembro;
	private JLabel cantidadTotal;
	private JLabel edadMedia;
	private String totalMiembros;

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public Vista() throws SQLException {
		miembro = new Miembro(0, "", 0, "");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 965, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(457, 165, 494, 230);
		contentPane.add(scrollPane);

		table_1 = new JTable();
		table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_1.setSurrendersFocusOnKeystroke(true);
		table_1.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				llenarCampos();
			}
		});

		scrollPane.setViewportView(table_1);
		this.pintarTabla();

		fieldNombre = new JTextField();
		fieldNombre.setBounds(516, 73, 96, 19);
		contentPane.add(fieldNombre);
		fieldNombre.setColumns(10);

		fieldEdad = new JTextField();
		fieldEdad.setBounds(665, 73, 38, 19);
		contentPane.add(fieldEdad);
		fieldEdad.setColumns(10);

		comboCargo = new JComboBox();
		comboCargo.setModel(new DefaultComboBoxModel(
				new String[] { "...", "Programador junior", "Programador senior", "Analista" }));
		comboCargo.setBounds(784, 72, 146, 21);
		contentPane.add(comboCargo);

		btnAgregarMiembro = new JButton("Agregar miembro");
		btnAgregarMiembro.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnAgregarMiembro.setBounds(10, 165, 414, 42);
		contentPane.add(btnAgregarMiembro);

		btnActualizarMiembro = new JButton("Actualizar miembro");
		btnActualizarMiembro.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnActualizarMiembro.setBounds(10, 227, 414, 42);
		contentPane.add(btnActualizarMiembro);

		btnAgregarMiembro.addActionListener(new ActionListener() {
			// Cada que se agrega un miembro se guarda la nueva lista, pinta la tabla, y
			// actualiza los datos de cantidad y edad media.
			public void actionPerformed(ActionEvent e) {
				guardar();
				actualizarCantidad();
				actualizarEdadMedia();
			}
		});

		btnActualizarMiembro.addActionListener(new ActionListener() {
			// Cada que se actualiza un miembro se pinta la tabla, y actualiza los datos
			// edad media.
			public void actionPerformed(ActionEvent e) {
				actualizar();
				actualizarEdadMedia();
			}
		});

		btnBorrarMiembro = new JButton("Borrar miembro");
		btnBorrarMiembro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Cada que se borra un miembro se pinta la tabla, y actualiza los datos de
				// cantidad y edad media.
				borrado();
				actualizarCantidad();
				actualizarEdadMedia();
			}
		});
		btnBorrarMiembro.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnBorrarMiembro.setBounds(10, 289, 414, 42);
		contentPane.add(btnBorrarMiembro);

		JButton btnMostrarEquipoCompleto = new JButton("Mostrar equipo completo");
		btnMostrarEquipoCompleto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					comboBuscarCargo.setSelectedItem("Buscar por cargo");
					pintarTabla();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnMostrarEquipoCompleto.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnMostrarEquipoCompleto.setBounds(10, 353, 414, 42);
		contentPane.add(btnMostrarEquipoCompleto);

		JLabel header = new JLabel("Equipo de programacion");
		header.setBackground(Color.LIGHT_GRAY);
		header.setFont(new Font("Verdana", Font.PLAIN, 20));
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setBounds(330, 10, 282, 36);
		contentPane.add(header);

		JLabel lblNewLabel = new JLabel("En este momento en nuestro equipo somos :");
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 68, 330, 24);
		contentPane.add(lblNewLabel);

		JLabel lblLaMediaDe = new JLabel("La media de edad de nuestro equipo es de :");
		lblLaMediaDe.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblLaMediaDe.setBounds(10, 109, 330, 24);
		contentPane.add(lblLaMediaDe);

		totalMiembros = miembro.contarMiembros();
		cantidadTotal = new JLabel(totalMiembros);
		cantidadTotal.setFont(new Font("Verdana", Font.PLAIN, 14));
		cantidadTotal.setBounds(338, 74, 46, 13);
		contentPane.add(cantidadTotal);

		edadMedia = new JLabel(miembro.mediaEdadMiembros());
		edadMedia.setFont(new Font("Verdana", Font.PLAIN, 14));
		edadMedia.setBounds(330, 115, 76, 13);
		contentPane.add(edadMedia);

		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(457, 74, 65, 13);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Edad:");
		lblNewLabel_2.setFont(new Font("Verdana", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(622, 74, 46, 13);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Cargo:");
		lblNewLabel_3.setFont(new Font("Verdana", Font.PLAIN, 13));
		lblNewLabel_3.setBounds(728, 68, 46, 24);
		contentPane.add(lblNewLabel_3);

		comboBuscarCargo = new JComboBox();
		comboBuscarCargo.addActionListener(new ActionListener() {
			// Cuando se invoca el metodo segun la seleccion se pinta una nueva tabla con el
			// cargo seleccionado
			public void actionPerformed(ActionEvent e) {
				try {
					pintarTablaCargos();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
		comboBuscarCargo.setFont(new Font("Verdana", Font.PLAIN, 15));
		comboBuscarCargo.setModel(new DefaultComboBoxModel(
				new String[] { "Buscar por cargo", "Programador junior", "Programador senior", "Analista" }));
		comboBuscarCargo.setAlignmentX(CENTER_ALIGNMENT);
		comboBuscarCargo.setBounds(457, 103, 484, 36);
		contentPane.add(comboBuscarCargo);

	}

	// Pinta la tabla con todos los miembros
	private void pintarTabla() throws SQLException {
		table_1.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Nombre", "Edad", "Cargo" }) {
			Class[] columnTypes = new Class[] { Integer.class, String.class, Integer.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		// Se instancia DefaultTableCellRenderer para modificar la alineacion de las
		// columnas
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table_1.setDefaultRenderer(String.class, centerRenderer);
		table_1.setDefaultRenderer(Integer.class, centerRenderer);

		ArrayList<Miembro> lista = new ArrayList<Miembro>();
		lista = (ArrayList<Miembro>) DAOMiembro.getInstance().obtenerLista();

		for (Miembro m : lista) {

			Object[] fila = new Object[4];

			fila[0] = m.getId();
			fila[1] = m.getNombre();
			fila[2] = m.getEdad();
			fila[3] = m.getCargo();

			((DefaultTableModel) table_1.getModel()).addRow(fila);

		}

	}

	protected void guardar() {

		if (fieldNombre.getText().length() == 0 || fieldEdad.getText().length() == 0
				|| comboCargo.getSelectedItem().equals("...")) {
			JOptionPane.showMessageDialog(null, "Debe completar todos los datos para agregar un miembro");

		} else {

			if (miembro.getId() > 0) {
				JOptionPane.showMessageDialog(null, "Para modificar un miembro presione el boton 'Actualizar Miembro'");
				limpiarCampos();
			} else {
				// comprobar que el nombre no tenga numeros
				if (fieldNombre.getText().matches(".*\\d.*")) {
					JOptionPane.showMessageDialog(null, "El nombre no puede contenter numeros");
				} else {
					try {
						miembro.setNombre(fieldNombre.getText());
						miembro.setEdad(Integer.parseInt(fieldEdad.getText()));
						miembro.setCargo(String.valueOf(comboCargo.getSelectedItem()));
						miembro.anadirMiembro();

						pintarTabla();
						limpiarCampos();

					} catch (NumberFormatException n1) {
						JOptionPane.showMessageDialog(null,
								"El nombre no puede contenter numeros y la edad tiene que ser un numero entero entre 1 y 120");

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}

	}

	protected void actualizar() {

		if (fieldNombre.getText().length() == 0 || fieldEdad.getText().length() == 0
				|| comboCargo.getSelectedItem().equals("...")) {
			JOptionPane.showMessageDialog(null, "Debe completar todos los campor para actualizar el miembro");
		} else {
			if (fieldNombre.getText().matches(".*\\d.*")) {
				JOptionPane.showMessageDialog(null, "El nombre no puede contenter numeros");
			} else {
				try {
					if (miembro.getId() > 0) {
						miembro.setNombre(fieldNombre.getText());
						miembro.setEdad(Integer.parseInt(fieldEdad.getText()));
						miembro.setCargo(String.valueOf(comboCargo.getSelectedItem()));
						miembro.modificarMiembro();

						limpiarCampos();
					} else {

						JOptionPane.showMessageDialog(null,
								"Para agregar un nuevo miembro presiona el boton 'Agregar miembro");
					}
					pintarTabla();
				} catch (NumberFormatException n1) {
					JOptionPane.showMessageDialog(null,
							"El nombre no puede contenter numeros y la edad tiene que ser un numero entero entre 1 y 120");

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	}

	private void limpiarCampos() {
		// este metodo sera invocado cada vez que se termine de realizar una accion para
		// limpiar los
		// campos y que no se pise el ID de la accion anterior
		fieldNombre.setText(null);
		fieldEdad.setText(null);
		comboCargo.setSelectedIndex(0);
		miembro.setId(0);

	}

	public void borrado() {
		DefaultTableModel tm = (DefaultTableModel) table_1.getModel();
		if (table_1.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(null, "Primero debe seleccionar el miembro que quiere borrar");
		} else {
			String dato = String.valueOf(tm.getValueAt(table_1.getSelectedRow(), 0));

			if (miembro.getId() > 0) {
				try {

					miembro.borrarMiembro(Integer.parseInt(dato));
					JOptionPane.showMessageDialog(null, "Miembro borrado con exito");
					pintarTabla();
					limpiarCampos();

				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	// trae los datos de loc campos completados
	protected void llenarCampos() {
		DefaultTableModel tm = (DefaultTableModel) table_1.getModel();
		String dato = String.valueOf(tm.getValueAt(table_1.getSelectedRow(), 0));

		try {
			miembro = DAOMiembro.getInstance().obtenerPorId(Integer.parseInt(dato));

			fieldNombre.setText(miembro.getNombre());
			fieldEdad.setText(Integer.toString(miembro.getEdad()));

			int indexCargo = 0;
			if (miembro.getCargo().equals("Programador junior")) {
				indexCargo = 1;
			} else if (miembro.getCargo().equals("Programador senior")) {
				indexCargo = 2;
			} else if (miembro.getCargo().equals("Analista")) {
				indexCargo = 3;
			}
			comboCargo.setSelectedIndex(indexCargo);

		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void pintarTablaCargos() throws SQLException {

		table_1.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Nombre", "Edad", "Cargo" }) {
			Class[] columnTypes = new Class[] { Integer.class, String.class, Integer.class, String.class };

			public Class getColumnClass(int columnIndex) {

				return columnTypes[columnIndex];
			}
		});
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table_1.setDefaultRenderer(String.class, centerRenderer);
		table_1.setDefaultRenderer(Integer.class, centerRenderer);
		Object buscarCargo = comboBuscarCargo.getSelectedItem();
		if (buscarCargo.equals("Buscar por cargo")) {
			pintarTabla();
		} else {
			try {
				ArrayList<Miembro> lista = new ArrayList<Miembro>();
				lista = (ArrayList<Miembro>) DAOMiembro.getInstance().buscarPorCargo(buscarCargo);

				for (Miembro m : lista) {

					Object[] fila = new Object[4];

					fila[0] = m.getId();
					fila[1] = m.getNombre();
					fila[2] = m.getEdad();
					fila[3] = m.getCargo();

					((DefaultTableModel) table_1.getModel()).addRow(fila);
					buscarCargo = null;

				}
			} catch (NullPointerException nullValue) {
				JOptionPane.showMessageDialog(null, "No hay miembros registrados con este cargo");
			}
		}
	}

	// pinta un nuevo Jlabel con el valor de cantidad de miembros actualizado
	public void actualizarCantidad() {
		contentPane.remove(cantidadTotal);
		contentPane.revalidate();
		contentPane.repaint();
		try {
			totalMiembros = miembro.contarMiembros();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		cantidadTotal = new JLabel(totalMiembros);
		cantidadTotal.setFont(new Font("Verdana", Font.PLAIN, 14));
		cantidadTotal.setBounds(338, 74, 46, 13);
		contentPane.add(cantidadTotal);
	}

	// pinta un nuevo Jlabel con el valor de edad media actualizado
	public void actualizarEdadMedia() {
		contentPane.remove(edadMedia);
		contentPane.revalidate();
		contentPane.repaint();
		try {
			edadMedia = new JLabel(miembro.mediaEdadMiembros());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		edadMedia.setFont(new Font("Verdana", Font.PLAIN, 14));
		edadMedia.setBounds(330, 115, 76, 13);
		contentPane.add(edadMedia);

	}

}
