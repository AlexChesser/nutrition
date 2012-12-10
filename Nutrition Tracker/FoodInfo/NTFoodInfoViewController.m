//
//  NTFoodInfoViewController.m
//  Nutrition Tracker
//
//  Created by Application Developer on 12-12-09.
//  Copyright (c) 2012 Alex Chesser. All rights reserved.
//

#import "NTFoodInfoViewController.h"

@interface NTFoodInfoViewController ()

@end

@implementation NTFoodInfoViewController
@synthesize FoodItem = _FoodItem;
@synthesize FoodData = _FoodData;
@synthesize DataTable = _DataTable;
@synthesize nav =_nav;
@synthesize WeightAmount = _WeightAmount;
-(void)setFoodID:(NSString *)foodID
{
    // Custom initialization
    NTAppDelegate *appDelegate = (NTAppDelegate *)[[UIApplication sharedApplication] delegate];
    NSString *sql = [NSString stringWithFormat: @"SELECT * FROM FOOD_DES WHERE NDB_No = '%@';", foodID];
    NSArray *foodItem = [appDelegate getQuery: sql];
    if ([foodItem count] == 1) {
        self.FoodItem = [foodItem objectAtIndex: 0];        
        sql = sql = [NSString stringWithFormat: @"SELECT  * FROM WEIGHT WHERE NDB_No = '%@'", foodID];
        
        self.Weights = [appDelegate getQuery:sql];
        
        sql = [NSString stringWithFormat: @"SELECT  def.NutrDesc, def.Units, d.* FROM NUT_DATA d LEFT JOIN NUTR_DEF def ON d.Nutr_No = def.Nutr_No WHERE NDB_No = '%@'", foodID];
        self.FoodData = [appDelegate getQuery:sql];

        
        
    }
}



- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    self.FoodTitle.text = [self.FoodItem valueForKey:@"Long_Desc"];
    self.nav.topItem.title = [self.FoodItem valueForKey:@"Long_Desc"];


}

-(IBAction)done:(id)sender{
    [self dismissViewControllerAnimated:YES completion:^{}];
}

-(IBAction)weightChange:(id)sender{
    [self.DataTable reloadData];
}

-(void)viewWillAppear:(BOOL)animated{

}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    //NSLog(@"%@",self.FoodGroupDescriptions);
    //return [self.FoodGroupDescriptions count];
    return [self.FoodData count];
    
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"NutrientDataCell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
    
    // Configure the cell...
    
    UIFont *myFont = [ UIFont fontWithName: @"Arial" size: 12.0 ];
    cell.textLabel.font  = myFont;
    NSDictionary *datapoint = [self.FoodData objectAtIndex: indexPath.row];
    cell.textLabel.text = [datapoint valueForKey: @"NutrDesc"];

    float NutVal = [[datapoint valueForKey: @"Nutr_Val"]floatValue];
    if ([self.WeightAmount.text intValue] != 100){
        NutVal = NutVal / 100 * [self.WeightAmount.text intValue];
    }
    
    NSString *u = [datapoint valueForKey: @"Units"];
    if ([u characterAtIndex:0] == 65533){
        u = @"µg";
    }

    NSString *amt = [NSString stringWithFormat:@"%1.2f%@",NutVal,u];
    cell.detailTextLabel.text = amt;
    
    //cell.detailTextLabel.text = [self.SearchResults objectAtIndex:indexPath.row];
    return cell;
}

#pragma mark - Table view delegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [self.WeightAmount resignFirstResponder];
    [self weightChange:self];
}

@end
