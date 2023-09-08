package com.algaworks.erp.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.algaworks.erp.model.Empresa;

public class Empresas implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Empresas() {

	}

	public Empresas(EntityManager manager) {
		this.manager = manager;
	}

	public Empresa byId(Long id) {
		return manager.find(Empresa.class, id);
	}

	public List<Empresa> search(String nome) {
		TypedQuery<Empresa> query = manager.createQuery("from Empresa where nomeFantasia like :nomeFantasia",
				Empresa.class);
		query.setParameter("nomeFantasia", nome + "%");

		return query.getResultList();
	}

	public List<Empresa> searchAll() {
		return manager.createQuery("from Empresa", Empresa.class).getResultList();
	}

	public Empresa register(Empresa empresa) {
		return manager.merge(empresa);
	}

	public void remove(Empresa empresa) {
		empresa = byId(empresa.getId());
		manager.remove(empresa);
	}
}
