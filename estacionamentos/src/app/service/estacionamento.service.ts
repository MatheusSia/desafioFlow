import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EstacionamentoService {

  constructor(private http: HttpClient) { }

  getEstoque():Observable<any[]> {
    return this.http.get<any[]>('http://localhost:8080/estoque');
  }

  getVeiculo():Observable<any[]>{
    return this.http.get<any[]>('http://localhost:8080/veiculos');
  }

  getMovimentacao():Observable<any[]>{
    return this.http.get<any[]>('http://localhost:8080/movimentacoes');
  }

  postCadastroNota(request: any):Observable<string>{
    return this.http.post<string>('http://localhost:8080/estoque/armazenar-dinheiro', request);
  }

  postCadastroVeiculo(request: any):Observable<string>{
    return this.http.post<string>('http://localhost:8080/veiculos', request);
  }

  postMovimentacao(request: any, placa: string):Observable<string>{
    return this.http.post<string>('http://localhost:8080/movimentacoes/'+placa+'/pagamento', [request]);
  }

}
