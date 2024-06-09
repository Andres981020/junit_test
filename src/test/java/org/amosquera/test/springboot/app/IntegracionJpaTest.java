package org.amosquera.test.springboot.app;

import org.amosquera.test.springboot.app.models.Cuenta;
import org.amosquera.test.springboot.app.repositories.CuentaRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integracion_jpa")
@DataJpaTest
public class IntegracionJpaTest {

    @Autowired
    CuentaRepository cuentaRespository;

    @Test
    void testFindById() {
        Optional<Cuenta> cuenta = cuentaRespository.findById(1L);
        assertTrue(cuenta.isPresent());
        assertEquals("Andres", cuenta.orElseThrow().getPersona());
    }

    @Test
    void testFindByPersona() {
        Optional<Cuenta> cuenta = cuentaRespository.findByPersona("Andres");
        assertTrue(cuenta.isPresent());
        assertEquals("Andres", cuenta.orElseThrow().getPersona());
        assertEquals("1000.00", cuenta.orElseThrow().getSaldo().toPlainString());
    }

    @Test
    void testFindByPersonaThrowException() {
        Optional<Cuenta> cuenta = cuentaRespository.findByPersona("Juan");
//        assertThrows(NoSuchElementException.class, () -> cuenta.orElseThrow());
        assertThrows(NoSuchElementException.class, cuenta::orElseThrow);
        assertFalse(cuenta.isPresent());
    }

    @Test
    void testFindAll() {
        List<Cuenta> cuentas = cuentaRespository.findAll();
        assertFalse(cuentas.isEmpty());
        assertEquals(2, cuentas.size());
    }

    @Test
    void testSave() {
        // Given
        Cuenta cuentaPepe = new Cuenta(null, "Pepe", new BigDecimal("3000"));

        // When
        Cuenta cuenta = cuentaRespository.save(cuentaPepe);

        // When
//        Cuenta cuenta = cuentaRespository.findByPersona("Pepe").orElseThrow();
//        Cuenta cuenta = cuentaRespository.findById(save.getId()).orElseThrow();

        // Then
        assertEquals("Pepe", cuenta.getPersona());
        assertEquals("3000", cuenta.getSaldo().toPlainString());
        assertEquals(3, cuenta.getId());
    }

    @Test
    void testUpdate() {
        // Given
        Cuenta cuentaPepe = new Cuenta(null, "Pepe", new BigDecimal("3000"));

        // When
        Cuenta cuenta = cuentaRespository.save(cuentaPepe);

        // When
//        Cuenta cuenta = cuentaRespository.findByPersona("Pepe").orElseThrow();
//        Cuenta cuenta = cuentaRespository.findById(save.getId()).orElseThrow();

        // Then
        assertEquals("Pepe", cuenta.getPersona());
        assertEquals("3000", cuenta.getSaldo().toPlainString());
//        assertEquals(3, cuenta.getId());

        // When
        cuenta.setSaldo(new BigDecimal("3800"));

        Cuenta cuentaActualizada = cuentaRespository.save(cuenta);

        // Then
        assertEquals("Pepe", cuentaActualizada.getPersona());
        assertEquals("3800", cuentaActualizada.getSaldo().toPlainString());
    }

    @Test
    void testDelete() {
        Cuenta cuenta = cuentaRespository.findById(2l).orElseThrow();
        assertEquals("John", cuenta.getPersona());

        cuentaRespository.delete(cuenta);

        assertThrows(NoSuchElementException.class, () -> {
//            cuentaRespository.findByPersona("John").orElseThrow();
            cuentaRespository.findById(2L).orElseThrow();
        });
        assertEquals(1, cuentaRespository.findAll().size());
    }
}
