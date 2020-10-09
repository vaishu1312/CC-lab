#include<stdio.h>

int main(){

FILE * fp;
char ch;
int count = 0; 
char fname[50];
printf("\nEnter the file name: ");
scanf("%s",fname);
fp = fopen(fname,"r"); 
if(fp==NULL)
{
	printf("\nFile doesn't exist or can't be opened\n");
	return 1;
}   
while((ch = fgetc(fp)) != EOF){ 
	if(ch ==' ' || ch == '\n')  
		count++;  
}
printf("\nNumber of words present in given file: %d\n", count);  
fclose(fp);

return 1;
}