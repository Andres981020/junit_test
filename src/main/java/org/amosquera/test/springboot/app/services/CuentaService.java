package org.amosquera.test.springboot.app.services;

import org.amosquera.test.springboot.app.models.Cuenta;

import java.math.BigDecimal;
import java.util.List;

public interface CuentaService {

    List<Cuenta> findAll();
    Cuenta findById(Long id);
    Cuenta save(Cuenta cuenta);

    void deteleById(Long id);
    int revisarTotalTransferencias(Long bancoId);
    BigDecimal revisarSaldo(Long cuentaId);
    void transferir(Long numeroCuentaOrigen, Long numeroCuentaDestino, BigDecimal monto, Long bancoId);
}
