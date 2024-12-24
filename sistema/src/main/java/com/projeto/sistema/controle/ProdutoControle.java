package com.projeto.sistema.controle;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.projeto.sistema.modelos.Produto;
import com.projeto.sistema.repositorios.ProdutoRepositorio;

@Controller
public class ProdutoControle {

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @GetMapping("/cadastroVenda")
    public ModelAndView cadastrar(Produto produto) {
        ModelAndView mv = new ModelAndView("administrativo/produtos/cadastro");
        mv.addObject("produto", produto);
        return mv;
    }

    @GetMapping("/listarVenda")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("/administrativo/produtos/lista");
        // Obter todos os produtos
        List<Produto> listaProdutos = produtoRepositorio.findAll();
        
        // Calcular o produto mais caro
        Produto produtoMaisCaro = listaProdutos.stream()
            .max(Comparator.comparingDouble(Produto::getTotal))
            .orElse(null);  // Se não houver produtos, retorna null

        // Calcular a média de preços (sem desconto)
        double mediaPreco = listaProdutos.stream()
            .mapToDouble(Produto::getPreco)
            .average()
            .orElse(0.0);  // Se não houver produtos, retorna 0.0

        // Passar as informações para a view
        mv.addObject("ListaProdutos", listaProdutos);
        mv.addObject("produtoMaisCaro", produtoMaisCaro);
        mv.addObject("mediaPreco", mediaPreco);
        
        return mv;
    }
    
    @GetMapping("/editarProduto/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Produto> produto = produtoRepositorio.findById(id);
        return cadastrar(produto.orElse(new Produto()));
    }

    @GetMapping("/removerProduto/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Produto> produto = produtoRepositorio.findById(id);
        produto.ifPresent(produtoRepositorio::delete);
        return listar();
    }

    @PostMapping("/salvarProduto")
    public ModelAndView salvar(Produto produto, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(produto);
        }

        // Remove qualquer unidade de medida antes do processamento
        String tamanhoBruto = produto.getTamanho();
        double peso = 0.0;

        if (tamanhoBruto != null && !"Digital".equalsIgnoreCase(produto.getUnidadeMedida())) {
            try {
                peso = Double.parseDouble(tamanhoBruto.replace(" KG", "").trim());
            } catch (NumberFormatException e) {
                peso = 0.0; // Se houver erro na conversão, considera-se peso 0
            }
        }

        // Definir tamanho com unidade de medida
        if ("Digital".equalsIgnoreCase(produto.getUnidadeMedida())) {
            produto.setTamanho(tamanhoBruto + " MB");
        } else if ("Físico".equalsIgnoreCase(produto.getUnidadeMedida())) {
            produto.setTamanho(peso + " KG");
        }

        // Cálculo do preço base
        double precoBase = produto.getPreco() != null ? produto.getPreco() : 0.0;
        double precoFinal = precoBase;

        // Verificar se a promoção está marcada como "Sim"
        if ("Sim".equalsIgnoreCase(produto.getPromocao())) {
            // Aplicar desconto de 10%
            precoFinal = precoBase - (precoBase * 0.10);
            produto.setValorDesconto(precoBase * 0.10); // Preencher o valor do desconto
        } else {
            // Caso contrário, atribuir 0 ao campo valorDesconto
            produto.setValorDesconto(0.0);
        }

        // Cálculo do frete
        double valorFrete = 0.0;
        if ("Físico".equalsIgnoreCase(produto.getUnidadeMedida())) {
            valorFrete = peso * 10.0; // R$10 por kg
        }

        // Atribuir valor do frete ao produto (caso deseje salvar no banco também)
        produto.setValorFrete(valorFrete);

        // Cálculo do total final
        double totalFinal = precoFinal + valorFrete;
        produto.setTotal(totalFinal);

        // Salvar o produto no banco de dados
        produtoRepositorio.saveAndFlush(produto);
        return cadastrar(new Produto());
    }
}
