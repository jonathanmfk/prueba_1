/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import controller.EditorialJpaController;
import entity.Editorial;

/**
 *
 * @author jonat
 */
public class EditorialDao {
    private EditorialJpaController editorialJpaController = new EditorialJpaController();
    private Editorial editorial = new Editorial();
    private String message = "";
    
    public String insertCliente(String nombre) {
        try {
            editorial.setId(Integer.BYTES);
            editorial.setNombre(nombre);
            editorialJpaController.create(editorial);
            message = "Editorial guardado correctamente";
        } catch (Exception e) {
            System.out.println("Error al guardar la editorial" + e.getMessage());
            message = "No se pudo guardar la editorial";
        }
        return message;
    }
    
    public String updateCliente(int id, String nombre) {
        try {
            editorial.setId(id);
            editorial.setNombre(nombre);
            editorialJpaController.edit(editorial);
            message = "Editorial actualizada correctamente";
        } catch (Exception e) {
            System.out.println("Error al actualizar la editorial" + e.getMessage());
            message = "No se pudo actualizar la editorial";
        }
        return message;
    }
}
