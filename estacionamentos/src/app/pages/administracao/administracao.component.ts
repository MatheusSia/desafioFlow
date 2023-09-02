import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EstacionamentoService } from 'src/app/service/estacionamento.service';

@Component({
  selector: 'app-administracao',
  templateUrl: './administracao.component.html',
  styleUrls: ['./administracao.component.scss']
})
export class AdministracaoComponent implements OnInit {
  
  estoque: any[] = [];
  formCadastro!: FormGroup;
  
  constructor(private estacionamentoService: EstacionamentoService, private fb: FormBuilder) { }
  ngOnInit(): void {

    this.formCadastro = this.fb.group({
      valor: [null, Validators.required],
      quantidade: [null, Validators.required]
    });

    this.getEstoque();
  }

  submitCadastroNota(){
    this.estacionamentoService.postCadastroNota(this.formCadastro.value).subscribe(() => {
        this.getEstoque();
      });

   }

  getEstoque(){
    this.estacionamentoService.getEstoque().subscribe((response) => {
      this.estoque=response;
    });
  }
}
