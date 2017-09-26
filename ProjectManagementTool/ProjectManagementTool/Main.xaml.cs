using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace ProjectManagementTool
{
    /// <summary>
    /// Interaction logic for Main.xaml
    /// </summary>
    public partial class Main : Window
    {
        public Main()
        {
            InitializeComponent();

            generateListItems(20);      //Generates 20 test items in the list for demonstration
        }

        private void generateListItems(int numberOfItems)
        {
            for (int i=0; i<numberOfItems; i++)
            {
                ListBoxItem item = new ListBoxItem();
                item.Content = "Item " + i;
                item.FontSize = 30;
                item.Height = 50;
               

                projectList.Items.Add(item);
            }
        }
    }
}
