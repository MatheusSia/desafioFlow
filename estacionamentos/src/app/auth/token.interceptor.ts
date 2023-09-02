import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor() {}
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    
    request = request.clone({
      setHeaders: {
        Authorization: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJJUlMiLCJzdWIiOiIxIiwiaWF0IjoxNjkzNjA5NjA0LCJleHAiOjE2OTM2OTYwMDR9.NsVLSCsF2Z8ASHYn69P1eK8tZ-hhWz8pP7o6VFoAhr0`
      }
    });
    return next.handle(request);
  }
}