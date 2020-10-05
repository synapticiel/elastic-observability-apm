import apm from '../rum'
import React, { Component } from 'react';
import { SERVER_URL } from '../constants.js'
import ReactTable from "react-table";
import 'react-table/react-table.css';
import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css'
import AddCar from './AddCar.js';
// import { CSVLink } from 'react-csv';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import Snackbar from '@material-ui/core/Snackbar';

class Carlist extends Component {
    constructor(props) {
        super(props);
        this.state = { cars: [], open: false, message: 'Welcome to this App' };
    }

    // Add new car
    addCar(car) {
        /**
         * Start a managed custom transaction.
         * The RUM agent will automatically add the fetch request and 
         * end the transaction once it is finished.
         */
        apm.startTransaction("add-new-car", "cars-transations", { managed: true });
        apm.addLabels(car);

        fetch(SERVER_URL + 'api/cars',
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(car)
            })
            .then(res => this.fetchCars())
            .catch(err => console.error(err))
    }

    fetchCars = () => {
        /**
 * Start a managed custom transaction.
 * The RUM agent will automatically add the fetch request and 
 * end the transaction once it is finished.
 */
        apm.startTransaction("fetch-cars-list", "cars-transations", { managed: true });

        fetch(SERVER_URL + 'api/cars')
            .then((response) => response.json())
            .then((responseData) => {

                this.setState({
                    //cars: responseData._embedded.cars,
                    cars: responseData,
                });
            })
            .catch(err => console.error(err));

    }

    renderEditable = (cellInfo) => {
        return (
            <div
                style={{ backgroundColor: "#fafafa" }}
                contentEditable
                suppressContentEditableWarning
                onBlur={e => {
                    const data = [...this.state.cars];
                    data[cellInfo.index][cellInfo.column.id] = e.target.innerHTML;
                    this.setState({ cars: data });
                }}
                dangerouslySetInnerHTML={{
                    __html: this.state.cars[cellInfo.index][cellInfo.column.id]
                }}
            />);
    }

    // Delete car
    onDelClick = (car) => {
        /**
 * Start a managed custom transaction.
 * The RUM agent will automatically add the fetch request and 
 * end the transaction once it is finished.
 */
        apm.startTransaction("delete-car", "cars-transations", { managed: true });
        apm.addLabels(car);

        fetch(SERVER_URL + 'api/cars/' + car.id, { method: 'DELETE' })
            .then(res => {
                this.setState({ open: true, message: 'Car deleted' });
                // this.fetchCars();
            })
            .catch(err => {
                this.setState({ open: true, message: 'Error when deleting' });
                console.error('This is an error when deleting and fetching cars ', err)
            })
    }

    // Update car
    updateCar(car) {
        /**
 * Start a managed custom transaction.
 * The RUM agent will automatically add the fetch request and 
 * end the transaction once it is finished.
 */
        apm.startTransaction("update-car", "cars-transations", { managed: true });
        apm.addLabels(car);

        fetch(SERVER_URL + 'api/cars/' + car.id,
            {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(car)
            })
            .then(res =>
                this.setState({ open: true, message: 'Changes saved' })
            )
            .catch(err =>
                this.setState({ open: true, message: 'Error when saving' })
            )
    }

    componentDidMount() {
        this.fetchCars();
    }


    generateError() {
        const err = new Error("This is an error generated on purpose for testing!");
        throw err;
    }

    confirmDelete = (car) => {
        confirmAlert({
            message: 'Are you sure to delete this car ? ' + JSON.stringify(car, 0, 2),
            buttons: [
                {
                    label: 'Yes',
                    onClick: () => this.onDelClick(car)
                },
                {
                    label: 'No',
                }]
        })
    }

    render() {
        const { cars } = this.state;

        const columns = [
            {
                Header: 'ID',
                accessor: 'id',
                show: false
            },
            {
                Header: "Full Name",
                id: "full",
                accessor: d =>
                    <div
                        dangerouslySetInnerHTML={{
                            __html: d.brand + " " + d.model
                        }}
                    />
            },
            {
                Header: 'Brand',
                accessor: 'brand',
                Cell: this.renderEditable,
            }, {
                Header: 'Model',
                accessor: 'model',
                Cell: this.renderEditable,
            }, {
                Header: 'Color',
                accessor: 'color',
                Cell: this.renderEditable,
            }, {
                Header: 'Year',
                accessor: 'year',
                Cell: this.renderEditable,
            }, {
                Header: 'Price $',
                accessor: 'price',
                Cell: this.renderEditable,
            },{
                Header: 'Market $',
                accessor: 'marketEstimate',
            }
            ,{
                Header: 'Number',
                accessor: 'registerNumber',
                Cell: this.renderEditable,
            },
            {
                id: 'savebutton',
                sortable: false,
                filterable: false,
                width: 100,
                accessor: 'id',
                Cell: ({ value, row }) => (<Button size="small" variant="text"
                    color="primary"
                    onClick={() => { this.updateCar(row) }}>Save</Button>)
            }, {
                id: 'delbutton',
                sortable: false,
                filterable: false,
                width: 100,
                accessor: 'id',
                Cell: ({ value, row }) => (<Button size="small" variant="text" color="secondary"
                    onClick={() => { this.confirmDelete(row) }}>Delete</Button>)
            }]

        // Carlist.js render() method's return statement
        return (
            <div className="App">
                <Grid container>
                    <Grid item>
                        <AddCar addCar={this.addCar} fetchCars={this.fetchCars} />
                    </Grid>
                    <Grid>
                        <Button variant="contained" color="secondary"
                            style={{ 'margin': '10px' }}
                            onClick={() => { this.generateError() }}>Error</Button>
                    </Grid>
                </Grid>
                <ReactTable data={cars} columns={columns}
                    filterable={true} pageSize={10}/>
                <Snackbar
                    style={{ width: 300, color: 'green' }}
                    open={this.state.open} onClose={this.handleClose}
                    autoHideDuration={1500} message={this.state.message} />
            </div>
        );
    }
    handleClose = (event, reason) => {
        this.setState({ open: false });
    };

}
export default Carlist;