import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Category} from "../model/category";
import {map} from "rxjs/operators";
import {Product} from "../model/product";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  baseUrl:string = 'http://localhost:9090/products';
  constructor(private http: HttpClient) { }

  getProducts(page: number, size: number): Observable<any> {

    return this.http.get<any>(`${this.baseUrl}/${page}/${size}`);
  }


  getProductsByCategoryId(id: string, page: number, size: number): Observable<any> {
if (page<1){
      page=1;
}
    const url = `${this.baseUrl}/category/${id}?page=${page}&size=${size}`;


    return this.http.get<any>(url, ).pipe(
      map(response => response)
    );
  }


  search(key:string,page:number, size:number): Observable<any> {
    const url = `${this.baseUrl}/by-name-or-description/${key}`;
    const params = { page: page.toString(), size: size.toString() };

    return this.http.get<any>(url, { params });
  }

}
