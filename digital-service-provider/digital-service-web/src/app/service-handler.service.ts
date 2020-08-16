import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class ServiceHandlerService {

  constructor(private http: HttpClient) { }


  public addService(service) {
    return this.http.post("http://localhost:8282/api/vix/digital/v1/services", service, { responseType: 'text' as 'json' });
  }

  public getServices() {
    return this.http.get("http://localhost:8282/api/vix/digital/v1/services");
  }

  public getService(id) {
    return this.http.get("http://localhost:8282/api/vix/digital/v1/services/" + id);
  }

  public deleteService(id) {
    return this.http.delete("http://localhost:8282/api/vix/digital/v1/services/" + id);
  }
}
