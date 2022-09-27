/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import controller.ProductoJpaController;
import entity.Producto;
import entity.Editorial;
import entity.TipoProducto;

/**
 *
 * @author jonat
 */
public class ProductoDao {
    private ProductoJpaController productoJpaController = new ProductoJpaController();
    private Producto producto = new Producto();
    private String message = "";
    
    public String insertCliente(String nombre, int cantidad, TipoProducto tipoProducto, Editorial editorial) {
        try {
            producto.setId(Integer.BYTES);
            producto.setNombre(nombre);
            producto.setCantidad(cantidad);
            producto.setTipoproductoid(tipoProducto);
            producto.setEditorialid(editorial);
            productoJpaController.create(producto);
            message = "producto guardado correctamente";
        } catch (Exception e) {
            System.out.println("Error al guardar el producto" + e.getMessage());
            message = "No se pudo guardar el producto";
        }
        return message;
    }
    
    public String updateCliente(int id, String nombre, int cantidad, TipoProducto tipoProducto, Editorial editorial) {
        try {
            producto.setId(id);
            producto.setNombre(nombre);
            producto.setCantidad(cantidad);
            producto.setTipoproductoid(tipoProducto);
            producto.setEditorialid(editorial);
            productoJpaController.edit(producto);
            message = "producto actualizado correctamente";
        } catch (Exception e) {
            System.out.println("Error al actualizar el producto" + e.getMessage());
            message = "No se pudo actualizar el producto";
        }
        return message;
    }
}
