import {Component, OnInit} from '@angular/core';
import {Product} from "../../../model/product";
import {ProductService} from "../../../service/product.service";
import {ActivatedRoute, RouterLink} from "@angular/router";

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  products: Product[] = [  ];
  constructor(private productService: ProductService, private activatedRoute: ActivatedRoute) {
  }
  ngOnInit(): void {
    const idExist = this.activatedRoute.snapshot.paramMap.has('id');
    const keyExist = this.activatedRoute.snapshot.paramMap.has('key');
    if (idExist) {
      const idCategory = this.activatedRoute.snapshot.paramMap.get('id');
      this.getProductsByCategoryId(idCategory);
    } else if (keyExist) {
      const key = this.activatedRoute.snapshot.paramMap.get('key');
      this.search(key);
    } else {
      this.getAllProducts();
    }
  }

  // tslint:disable-next-line:typedef
  getAllProducts() {
    this.productService.getProducts().subscribe(
      result => {
        console.log('Products arrived:', result); // ✅ هنا هتشوفهم في console
        this.products = result;
      },
      error => console.error('Error fetching products', error) // لو فيه مشكلة
    );
  }
  // tslint:disable-next-line:typedef
  getProductsByCategoryId(id){
    this.productService.getProductsByCategoryId(id).subscribe(
      result => this.products = result
    );
  }
  // tslint:disable-next-line:typedef
  search(key){
    this.productService.search(key).subscribe(
      result => this.products = result
    );
  }
}
