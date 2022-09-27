/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import controller.ProductosxventaJpaController;
import entity.Productosxventa;
import entity.Producto;
import entity.Venta;

/**
 *
 * @author jonat
 */
public class ProductosxVentaDao {
    private ProductosxventaJpaController productosxventaJpaController = new ProductosxventaJpaController();
    private Productosxventa productosxventa = new Productosxventa();
    private String message = "";
    
    public String insertCliente(int cantidad, Producto producto, Venta venta) {
        try {
            productosxventa.setId(Integer.BYTES);
            productosxventa.setCantidad(cantidad);
            productosxventa.setProductoId(producto);
            productosxventa.setVentaId(venta);
            productosxventaJpaController.create(productosxventa);
            message = "inventario guardado correctamente";
        } catch (Exception e) {
            System.out.println("Error al guardar el inventario" + e.getMessage());
            message = "No se pudo guardar el inventario";
        }
        return message;
    }
    
    public String updateCliente(int id, int cantidad, Producto producto, Venta venta) {
        try {
            productosxventa.setId(id);
            productosxventa.setCantidad(cantidad);
            productosxventa.setProductoId(producto);
            productosxventa.setVentaId(venta);
            productosxventaJpaController.edit(productosxventa);
            message = "inventario actualizado correctamente";
        } catch (Exception e) {
            System.out.println("Error al actualizar el inventario" + e.getMessage());
            message = "No se pudo actualizar el inventario";
        }
        return message;
    }
}
