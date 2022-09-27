/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import controller.VentaJpaController;
import entity.Venta;
import entity.Cliente;

/**
 *
 * @author jonat
 */
public class VentaDao {
    private VentaJpaController ventaJpaController = new VentaJpaController();
    private Venta editorial = new Venta();
    private String message = "";
    
    public String insertCliente(Cliente cliente) {
        try {
            editorial.setId(Integer.BYTES);
            editorial.setClienteId(cliente);
            ventaJpaController.create(editorial);
            message = "Venta guardado correctamente";
        } catch (Exception e) {
            System.out.println("Error al guardar la Venta" + e.getMessage());
            message = "No se pudo guardar la Venta";
        }
        return message;
    }
    
    public String updateCliente(int id, Cliente cliente) {
        try {
            editorial.setId(id);
            editorial.setClienteId(cliente);
            ventaJpaController.edit(editorial);
            message = "Venta actualizada correctamente";
        } catch (Exception e) {
            System.out.println("Error al actualizar la Venta" + e.getMessage());
            message = "No se pudo actualizar la Venta";
        }
        return message;
    }
}
