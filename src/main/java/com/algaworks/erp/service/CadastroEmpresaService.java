package com.algaworks.erp.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.erp.model.Empresa;
import com.algaworks.erp.repository.Empresas;
import com.algaworks.erp.util.Transacional;

public class CadastroEmpresaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Empresas empresas;

	@Transacional
	public void save(Empresa empresa) {
		empresas.register(empresa);
	}

	@Transacional
	public void remove(Empresa empresa) {
		empresas.remove(empresa);
	}

}