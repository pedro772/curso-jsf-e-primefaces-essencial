package com.algaworks.erp.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.algaworks.erp.model.Empresa;
import com.algaworks.erp.model.RamoAtividade;
import com.algaworks.erp.model.TipoEmpresa;

import io.github.cdimascio.dotenv.Dotenv;

public class CamadaPersistencia {
	public static void main(String[] args) {
		// Load the .env file
		Dotenv dotenv = Dotenv.load();

		// Access the environment variables
		String username = dotenv.get("DB_USERNAME");
		String password = dotenv.get("DB_PASSWORD");

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("AlgaWorksPU");

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		// Declarando os repositórios
		RamoAtividades ramoAtividades = new RamoAtividades(em);
		Empresas empresas = new Empresas(em);

		// Buscando as informações do banco
		List<RamoAtividade> listaDeRamoAtividades = ramoAtividades.search("");
		List<Empresa> listaDeEmpresas = empresas.search("");
		System.out.println(listaDeEmpresas);

		// Criando uma empresa
		Empresa empresa = new Empresa();
		empresa.setNomeFantasia("João da Silva");
		empresa.setCnpj("41.952.519/0001-57");
		empresa.setRazaoSocial("João da Silva 41952519000157");
		empresa.setTipo(TipoEmpresa.MEI);
		empresa.setDataFundacao(new Date());
		empresa.setRamoAtividade(listaDeRamoAtividades.get(0));

		// Salvando a empresa
		empresas.register(empresa);
		em.getTransaction().commit();

		// Verificando se a inserção funcionou
		List<Empresa> listaDeEmpresas2 = empresas.search("");
		System.out.println(listaDeEmpresas2);

		em.close();
		emf.close();
	}
}
