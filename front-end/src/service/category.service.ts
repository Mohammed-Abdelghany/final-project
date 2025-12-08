import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {Category} from "../model/category";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import * as http from "node:http";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  categoriesUrl = 'http://localhost:9090/categories/';

  constructor(private http: HttpClient) { }

  getCategories(page: number, size: number): Observable<Category[]> {
    const url = `${this.categoriesUrl}${page}/${size}`;
    return this.http.get<any>(url).pipe(  // <--- any مش Category[]
      map(response => response.content || []) // <--- ناخد Array داخل content
    );
  }



}
