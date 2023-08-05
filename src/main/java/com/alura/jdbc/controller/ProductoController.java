package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;
import com.alura.jdbc.dao.ProductoDAO;

import java.sql.*;
import java.util.*;

public class ProductoController {
	private ProductoDAO productoDAO;
	public ProductoController() {
		this.productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
	}

	public void modificar(String nombre, String descripcion, Integer cantidad,  Integer id) throws SQLException {
		final Connection con = new ConnectionFactory().recuperaConexion();
		try(con){
			final PreparedStatement statement = con.prepareStatement(
					"UPDATE producto SET NOMBRE = ?, DESCRIPCION = ?, CANTIDAD = ? WHERE ID = ?"
			);
			try(statement) {
				statement.setString(1, nombre);
				statement.setString(2, descripcion);
				statement.setInt(3, cantidad);
				statement.setInt(4, id);
				statement.execute();
			}
		}
	}

	public int eliminar(Integer id) throws SQLException{
		final Connection con = new ConnectionFactory().recuperaConexion();
		try(con){

		final PreparedStatement statement = con.prepareStatement("DELETE FROM producto WHERE ID = ?");
			try (statement) {
				statement.setInt(1, id);
				statement.execute();
				return statement.getUpdateCount();
			}
		}

	}

	public List<Producto> listar(){
		return productoDAO.listar();


	}
	public List<Producto> listar(Categoria categoria){
		return productoDAO.listar(categoria.getId());
	}

    public void guardar(Producto producto, Integer categoriaId) {
		producto.setCategoriaId(categoriaId);
		productoDAO.guardar(producto);
	}
}
