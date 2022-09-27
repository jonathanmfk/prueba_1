/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import controller.ClienteJpaController;
import entity.Cliente;

/**
 *
 * @author jonat
 */
public class ClienteDao {
    private ClienteJpaController clienteJpaController = new ClienteJpaController();
    private Cliente cliente = new Cliente();
    private String message = "";
    
    public String insertCliente(String nombre, String apellido, String identificacion) {
        try {
            cliente.setId(Integer.BYTES);
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setIdentificacion(identificacion);
            clienteJpaController.create(cliente);
            message = "Cliente guardado correctamente";
        } catch (Exception e) {
            System.out.println("Error al guardar el cliente" + e.getMessage());
            message = "No se pudo guardar el cliente";
        }
        return message;
    }
    
    public String updateCliente(int id, String nombre, String apellido, String identificacion) {
        try {
            cliente.setId(id);
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setIdentificacion(identificacion);
            clienteJpaController.edit(cliente);
            message = "Cliente actualizado correctamente";
        } catch (Exception e) {
            System.out.println("Error al actualizar el cliente" + e.getMessage());
            message = "No se pudo actualizar el cliente";
        }
        return message;
    }
    
}
