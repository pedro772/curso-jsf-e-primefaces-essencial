package com.algaworks.erp.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.algaworks.erp.model.Empresa;
import com.algaworks.erp.model.RamoAtividade;
import com.algaworks.erp.model.TipoEmpresa;
import com.algaworks.erp.repository.Empresas;
import com.algaworks.erp.repository.RamoAtividades;
import com.algaworks.erp.service.CadastroEmpresaService;
import com.algaworks.erp.util.FacesMessages;

@Named
@ViewScoped
public class GestaoEmpresasBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private Empresas empresas;

	@Inject
	private FacesMessages messages;

	@Inject
	private RamoAtividades ramoAtividades;

	@Inject
	private CadastroEmpresaService cadastroEmpresaService;

	private List<Empresa> listaEmpresas;

	private String termoPesquisa;

	private Converter ramoAtividadeConverter;

	private Empresa empresa;

	public void prepareNewEmpresa() {
		empresa = new Empresa();
	}

	public void prepareEdition() {
		ramoAtividadeConverter = new RamoAtividadeConverter(Arrays.asList(empresa.getRamoAtividade()));
	}

	public void save() {
		cadastroEmpresaService.save(empresa);
		updateRecords();
		messages.info("Empresa salva com sucesso!");

		RequestContext.getCurrentInstance().update(Arrays.asList("frm:messages", "frm:empresasDataTable"));
	}

	public void delete() {
		cadastroEmpresaService.remove(empresa);
		empresa = null;
		updateRecords();
		messages.info("Empresa excluída com sucesso!");
	}

	public void search() {
		listaEmpresas = empresas.search(termoPesquisa);

		if (listaEmpresas.isEmpty()) {
			messages.info("Sua consulta não retornou registros.");
		}
	}

	public void todasEmpresas() {
		listaEmpresas = empresas.searchAll();
	}

	public List<RamoAtividade> completeRamoAtividade(String termo) {
		List<RamoAtividade> listaRamoAtividades = ramoAtividades.search(termo);

		ramoAtividadeConverter = new RamoAtividadeConverter(listaRamoAtividades);

		return listaRamoAtividades;
	}

	private boolean hasBeenSearched() {
		return termoPesquisa != null && !"".equals(termoPesquisa);
	}

	public void updateRecords() {
		if (hasBeenSearched()) {
			search();
		} else {
			todasEmpresas();
		}
	}

	public List<Empresa> getListaEmpresas() {
		return listaEmpresas;
	}

	public String getTermoPesquisa() {
		return termoPesquisa;
	}

	public void setTermoPesquisa(String termoPesquisa) {
		this.termoPesquisa = termoPesquisa;
	}

	public TipoEmpresa[] getTiposEmpresa() {
		return TipoEmpresa.values();
	}

	public Converter getRamoAtividadeConverter() {
		return ramoAtividadeConverter;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public boolean isEmpresaSelected() {
		return empresa != null && empresa.getId() != null;
	}
}
