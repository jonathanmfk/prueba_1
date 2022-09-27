/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import controller.TipoProductoJpaController;
import entity.TipoProducto;

/**
 *
 * @author jonat
 */
public class TipoProductoDao {
    private TipoProductoJpaController tipoProductoJpaController = new TipoProductoJpaController();
    private TipoProducto tipoProducto = new TipoProducto();
    private String message = "";
    
    public String insertCliente(String categoria) {
        try {
            tipoProducto.setId(Integer.BYTES);
            tipoProducto.setCategoria(categoria);
            tipoProductoJpaController.create(tipoProducto);
            message = "Tipo de producto guardado correctamente";
        } catch (Exception e) {
            System.out.println("Error al guardar el Tipo de producto" + e.getMessage());
            message = "No se pudo guardar el Tipo de producto";
        }
        return message;
    }
    
    public String updateCliente(int id, String categoria) {
        try {
            tipoProducto.setId(id);
            tipoProducto.setCategoria(categoria);
            tipoProductoJpaController.edit(tipoProducto);
            message = "Tipo de producto actualizado correctamente";
        } catch (Exception e) {
            System.out.println("Error al actualizar el Tipo de producto" + e.getMessage());
            message = "No se pudo actualizar el Tipo de producto";
        }
        return message;
    }
}
