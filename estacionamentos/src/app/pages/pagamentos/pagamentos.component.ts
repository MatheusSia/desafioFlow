import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { EstacionamentoService } from 'src/app/service/estacionamento.service';

@Component({
  selector: 'app-pagamentos',
  templateUrl: './pagamentos.component.html',
  styleUrls: ['./pagamentos.component.scss'],
})
export class PagamentosComponent implements OnInit {
  public paramPlaca = '';

  movimentacao: any[] = [];
  formCadastro!: FormGroup;

  constructor(private route: ActivatedRoute, private estacionamentoService: EstacionamentoService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.formCadastro = this.fb.group({
      valor: [null, Validators.required],
      quantidade: [null, Validators.required]
    });
    this.getMovimentacao();
    

    this.route.params.subscribe((res) => {
      if (res['placa']) {
        this.paramPlaca = res['placa'];
      }
    });
  }

  submitCadastroMovimentacao(){
    this.estacionamentoService.postMovimentacao(this.formCadastro.value, this.paramPlaca).subscribe(() => {
      this.getMovimentacao();
    });

  }

  getMovimentacao(){
    this.estacionamentoService.getMovimentacao().subscribe((response) => {
      this.movimentacao=response;
    });
  }
}
