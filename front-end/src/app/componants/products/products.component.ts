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

  products: Product[] = [];
  messageAr: string = "";
  messageEn: string = "";

  page: number = 1;
  pageSize: number = 10;
  collectionSize: number = 0;

  isLoading: boolean = false; // ← أضف السطر ده

  constructor(private productService: ProductService, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(
      () => this.loadProducts()
    )
  }

  loadProducts(){
    let idExist = this.activatedRoute.snapshot.paramMap.has("id");
    let keyExist = this.activatedRoute.snapshot.paramMap.has("key");
    if (idExist) {
      let idCategory = this.activatedRoute.snapshot.paramMap.get("id");
      this.getProductsByCategoryId(idCategory);
    } else if (keyExist) {
      let key = this.activatedRoute.snapshot.paramMap.get("key");
      this.search(key);
    } else {
      this.getAllProducts();
    }
  }

  getAllProducts() {
    this.isLoading = true;

    this.productService.getProducts(this.page, this.pageSize).subscribe({
      next: (result) => {
        console.log("✅ Full Response:", result); // شوف الـ response كامل
        console.log("✅ Products:", result.products);
        console.log("✅ Total Items:", result.totalItems);

        this.products = result.content;
        this.collectionSize = result.totalPages;
        this.clearMessages();
        this.isLoading = false;
      },
      error: (errorResponse) => {
        console.error("❌ Full Error:", errorResponse);
        console.error("❌ Status:", errorResponse.status);
        console.error("❌ Error Body:", errorResponse.error);

        this.products = [];
        this.messageAr = "حدث خطأ في جلب المنتجات";
        this.messageEn = "Failed to load products";
        this.isLoading = false;
      }
    });
  }
  getProductsByCategoryId(id: string | null) {

    if (!id) return;

    this.isLoading = true;

    this.productService.getProductsByCategoryId(id, this.page, this.pageSize).subscribe({
      next: (result) => {
        // result.content هو Array
        this.products = result.content || [];
        this.collectionSize = result.totalItems || 0;

        this.clearMessages();
        this.isLoading = false;
      },
      error: (errorResponse) => {
        this.products = [];
        this.collectionSize = 0;
        this.messageAr = errorResponse.error?.bundleMessage?.message_ar || "حدث خطأ";
        this.messageEn = errorResponse.error?.bundleMessage?.message_en || "Something went wrong";
        this.isLoading = false;
      }
    });
  }



  search(key: string | null) {
    this.isLoading = true;

    this.productService.search(key, this.page , this.pageSize).subscribe({
      next: (result) => {
        this.products = result.content;
        this.collectionSize = result.totalPages;
        this.clearMessages();
        this.isLoading = false;
      },
      error: (errorResponse) => {
        this.products = [];
        this.messageAr = errorResponse.error?.bundleMessage?.message_ar || "حدث خطأ في البحث";
        this.messageEn = errorResponse.error?.bundleMessage?.message_en || "Search failed";
        this.isLoading = false;
      }
    });
  }

  doPagination() {
    this.loadProducts();
  }

  changePageSize(event: Event) {
    this.pageSize = +(<HTMLInputElement>event.target).value;
    this.page = 1; // نرجع للصفحة الأولى
    this.loadProducts();
  }

  addToCart(product: Product) {
    // TODO: Implement add to cart logic
    console.log('Added to cart:', product);
  }

  private clearMessages() {
    this.messageAr = "";
    this.messageEn = "";
  }
}
