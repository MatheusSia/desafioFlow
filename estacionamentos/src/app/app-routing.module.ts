import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdministracaoComponent } from './pages/administracao/administracao.component';
import { CadastroComponent } from './pages/cadastro/cadastro.component';
import { PagamentosComponent } from './pages/pagamentos/pagamentos.component';

const routes: Routes = [
  {
    path: '',
    component: AdministracaoComponent,
  },
  {
    path: 'cadastro',
    component: CadastroComponent,
  },
  {
    path: 'pagamentos/:placa',
    component: PagamentosComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
