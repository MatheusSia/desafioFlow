import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EstacionamentoService } from 'src/app/service/estacionamento.service';

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.scss'],
})
export class CadastroComponent implements OnInit {
  [x: string]: any;

  veiculo: any[] = [];
  formCadastro!: FormGroup;

  constructor(private estacionamentoService: EstacionamentoService, private fb: FormBuilder){}
  ngOnInit(): void {

    this.formCadastro = this.fb.group({
      placa: [null, Validators.required],
      tipo: [null, Validators.required]
    });
    this.getVeiculo();
  }

  submitCadastroVeiculo(){
    this.estacionamentoService.postCadastroVeiculo(this.formCadastro.value).subscribe(() => {
      this.getVeiculo();
    });

  }

    getVeiculo(){
    this.estacionamentoService.getVeiculo().subscribe((response) => {
      this.veiculo=response;
    });
  }

}
