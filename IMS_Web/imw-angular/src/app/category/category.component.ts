import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../service/api.service';

interface Category{
  id:string,
  name:string
}

@Component({
  selector: 'app-category',
  imports: [CommonModule,FormsModule],
  templateUrl: './category.component.html',
  styleUrl: './category.component.css'
})

export class CategoryComponent implements OnInit{
categories:Category[]=[];
categoryName:string='';
isEditing:boolean=false;
editingCategoryId:string | null=null;

message:string | null=null;

constructor(private apiService:ApiService){}


  ngOnInit(): void {
   this.getCategories();
  }
  getCategories():void{
this.apiService.getAllCategory().subscribe({
  next:(res:any)=>{
    if(res.status===200){
    this.categories=res.categories;
    }
  },
  error:(error)=>{
    this.showMessage(error?.error?.message || error?.message || "Unable to get all categories"+error);
   
  }
})
  }
  
  //ADD new Category

  addCategry():void{
    if(!this.categoryName){
      this.showMessage("Category name is required");
      return;
    }
this.apiService.createCategory({name:this.categoryName}).subscribe({
  next:(res:any)=>{
    if(res.status===200){
    this.showMessage("Category added successfully");
    this.categoryName='';
    this.getCategories();
    }
  },
  error:(error)=>{
    this.showMessage(error?.error?.message || error?.message || "Unable to Save category"+error);
   
  }
})
  }

  //Edit Category

  editCategry():void{
    if(!this.editingCategoryId || !this.categoryName){
      this.showMessage("Category name is required");
      return;
    }
this.apiService.updatecategory(this.editingCategoryId,{name:this.categoryName}).subscribe({
  next:(res:any)=>{
    if(res.status===200){
    this.showMessage("Category updated successfully");
    this.categoryName='';
    this.editingCategoryId='';
    this.isEditing=false;
    this.getCategories();
    }
  },
  error:(error)=>{
    this.showMessage(error?.error?.message || error?.message || "Unable to Edit category"+error);
   
  }
})
  }

//set Category to edit

handleEditCategory(category:Category):void{
  this.isEditing=true;
this.editingCategoryId=category.id;
  this.categoryName=category.name;
}

//delete the category

handleDeleteCategory(categoryId:string):void{
  if(window.confirm("Are you sure you want to delete this category")){
  this.apiService.deleteCategory(categoryId).subscribe({
    next:(res:any)=>{
      if(res.status===200){
      this.showMessage("Category deleted successfully");
      this.getCategories();
      }
    },
    error:(error)=>{
      this.showMessage(error?.error?.message || error?.message || "Unable to deleted category"+error);
     
    }
  });
  }
}
  showMessage(message:string){
    this.message=message;
   setTimeout(()=>{
    this.message='';
   },4000)
  }
}
